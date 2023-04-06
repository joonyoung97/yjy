package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.BcListDto;
import com.jylove.mpc.mpc.entity.BcList;
import com.jylove.mpc.mpc.repository.BcListAnswerRepository;
import com.jylove.mpc.mpc.repository.BcListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class BcListService {


    @Value("${bcListImgLocation}")
    private String bcListImgLocation;

    private final BcListRepository bcListRepository;
    private final BcListAnswerRepository bcListAnswerRepository;
    private final BcListAnswerService bcListAnswerService;

    private final FileService fileService;

    @Transactional(readOnly = true)  // 데이터 베이스에서 데이터 추가, 수정, 삭제, 조회 -  오류가 발생할 경우 오류 발생 이전상태로 돌릴 수 있다.
    public Page<BcList> getBcListPage(Pageable pageable) {
        return bcListRepository.getBcListPage(pageable);
    }

    @Transactional(readOnly = true)  // 데이터 베이스에서 데이터 추가, 수정, 삭제, 조회 -  오류가 발생할 경우 오류 발생 이전상태로 돌릴 수 있다.
    public Page<BcList> getUserBcListPage(String email, Pageable pageable) {
        return bcListRepository.getUserBcListPage(email, pageable);
    }

    @Transactional(readOnly = true)
    public BcListDto getBcListDtl(Long bcListId){
        BcList bcList = bcListRepository.findById(bcListId).orElseThrow(EntityNotFoundException::new);
        BcListDto bcListDto = BcListDto.of(bcList);
        return bcListDto;
    }





    public Long saveBcList(BcListDto bcListDto, MultipartFile multipartFile) throws Exception { //파일이 있다면 파일업로드와 같이
        String oriImgName = multipartFile.getOriginalFilename(); //업로드 된 파일의 파일명
        String imgName = "";
        String imgUrl = "";

        //파일이 있으면 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(bcListImgLocation, oriImgName, multipartFile.getBytes()); //파일 저장 메서드
            //사용자가 공지사항에 이미지를 등록했다면 저장할 경로와 파일의 이름,
            //파일을 파일의 바이트 배열을 파일 업로드 파라미터로 uploadFile 메서드 호출
            //호출 결과 실제 저장되는 파일의 이름을 imgName에 저장
            imgUrl = "/images/bclist/" + imgName;
            //저장한 상품 이미지를 불러올 경로를 설정
            //실제 경로는 C:/mpcImage/notice 이지만 웹에서 브라우저로 접속을 하였을때
            //C:/mpcImage/notice의 경로를 사용할 권한은 주어지지 않는다.
            //그래서 내부에서만 실제경로를 사용하고 외부에서는 접근권한이 있는 경로로
            //사용하도록 리소스 연결 작업을 해주었다.
            //즉 외부(브라우저를 통한 접속)에서는 static하위 디렉토리인 images를 사용하도록 되어있다.
        }

        BcList bcList = new BcList();
        bcList = bcList.createBcList(bcListDto, oriImgName, imgName, imgUrl);
        bcListRepository.save(bcList);

        bcListAnswerService.saveBcListAnswer(bcList);

        return bcList.getId();
    }


    public Long updateBcList(BcListDto bcListDto, MultipartFile multipartFile) throws Exception{
        // 영속 상태
        BcList bcList = bcListRepository.findById(bcListDto.getId()).orElseThrow(EntityNotFoundException::new);

        // 파일이 있다면
        if (!multipartFile.isEmpty()){
            // 기존 이미지가 있다면 삭제
            if (!StringUtils.isEmpty(bcList.getBcOriImgName())){
                fileService.deleteFile(bcListImgLocation+"/"+bcList.getBcImgName());
            }

            String oriImgName = multipartFile.getOriginalFilename();
            String imgName = fileService.uploadFile(bcListImgLocation, oriImgName, multipartFile.getBytes());
            String imgUrl = "/images/bclist/" +imgName;

            bcListDto.setBcOriImgName(oriImgName);
            bcListDto.setBcImgName(imgName);
            bcListDto.setBcImgUrl(imgUrl);
        }

        else {
            bcListDto.setBcOriImgName(bcList.getBcOriImgName());
            bcListDto.setBcImgName(bcList.getBcImgName());
            bcListDto.setBcImgUrl(bcList.getBcImgUrl());
        }

        bcList.updateBcList(bcListDto);

        return bcList.getId();

    }


    public void deleteBcList(Long bcListId) {
        bcListAnswerRepository.deleteByBcListId(bcListId);
        bcListRepository.deleteById(bcListId);
    }
}
