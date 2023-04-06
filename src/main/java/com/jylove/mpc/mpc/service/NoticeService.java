package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.NoticeFormDto;
import com.jylove.mpc.mpc.dto.NoticeSearchDto;
import com.jylove.mpc.mpc.entity.Notice;
import com.jylove.mpc.mpc.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    @Value("${noticeImgLocation}")
    private String noticeImgLocation;

    private final NoticeRepository noticeRepository;

    private final FileService fileService;

    @Transactional(readOnly = true) //데이터베이스에서 데이터 추가, 수정, 삭제, 조회 - 오류가 발생할 경우 오류 발생 이전상태로 돌릴수 있다.
    public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable){
        return noticeRepository.getAdminNoticePage(noticeSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public NoticeFormDto getNoticeDtl(Long noticeId){
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);
        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice);

        return noticeFormDto;
    }

    public Long saveNotice(NoticeFormDto noticeFormDto, MultipartFile multipartFile) throws Exception { //파일이 있다면 파일업로드와 같이
        String oriImgName = multipartFile.getOriginalFilename(); //업로드 된 파일의 파일명
        String imgName = "";
        String imgUrl = "";

        //파일이 있으면 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(noticeImgLocation, oriImgName, multipartFile.getBytes()); //파일 저장 메서드
            //사용자가 공지사항에 이미지를 등록했다면 저장할 경로와 파일의 이름,
            //파일을 파일의 바이트 배열을 파일 업로드 파라미터로 uploadFile 메서드 호출
            //호출 결과 실제 저장되는 파일의 이름을 imgName에 저장
            imgUrl = "/images/notice/" + imgName;
            //저장한 상품 이미지를 불러올 경로를 설정
            //실제 경로는 C:/mpcImage/notice 이지만 웹에서 브라우저로 접속을 하였을때
            //C:/mpcImage/notice의 경로를 사용할 권한은 주어지지 않는다.
            //그래서 내부에서만 실제경로를 사용하고 외부에서는 접근권한이 있는 경로로
            //사용하도록 리소스 연결 작업을 해주었다.
            //즉 외부(브라우저를 통한 접속)에서는 static하위 디렉토리인 images를 사용하도록 되어있다.
        }

        Notice notice = new Notice();
        notice = notice.createNotice(noticeFormDto, oriImgName, imgName, imgUrl);
        noticeRepository.save(notice);


        return notice.getId();
    }

    public Long updateNotice(NoticeFormDto noticeFormDto, MultipartFile multipartFile) throws Exception{
        //영속 상태
        Notice notice = noticeRepository.findById(noticeFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        //파일이 있다면
        if(!multipartFile.isEmpty()){
            //기존 이미지가 있다면 삭제
            if(!StringUtils.isEmpty(notice.getBcOriImgName())){
                fileService.deleteFile(noticeImgLocation+"/"+notice.getBcImgName());
            }

            String oriImgName = multipartFile.getOriginalFilename(); //업로드 된 파일의 파일명
            String imgName = fileService.uploadFile(noticeImgLocation, oriImgName, multipartFile.getBytes()); //파일 저장 메서드
            String imgUrl = "/images/notice/" +imgName;

            noticeFormDto.setBcOriImgName(oriImgName);
            noticeFormDto.setBcImgName(imgName);
            noticeFormDto.setBcImgUrl(imgUrl);
        }
        else{
            noticeFormDto.setBcOriImgName(notice.getBcOriImgName());
            noticeFormDto.setBcImgName(notice.getBcImgName());
            noticeFormDto.setBcImgUrl(notice.getBcImgUrl());
        }

        notice.updateNotice(noticeFormDto); //영속 상태이므로 객체에 저장 시 변경을 감지하여 DB에 업데이트 된다.

        return notice.getId();
    }

    public void deleteNotice(Long noticeId) throws Exception{
        //영속 상태
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);

        //기존 이미지가 있다면 삭제
        if(!StringUtils.isEmpty(notice.getBcOriImgName())){
            fileService.deleteFile(noticeImgLocation+"/"+notice.getBcImgName());
        }

        noticeRepository.deleteById(noticeId);
    }
}
