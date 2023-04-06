package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.AdAcPageDto;
import com.jylove.mpc.mpc.dto.AdacFormDto;
import com.jylove.mpc.mpc.dto.AdacImgDto;
import com.jylove.mpc.mpc.dto.AdacSearchDto;
import com.jylove.mpc.mpc.entity.AdAcPage;
import com.jylove.mpc.mpc.entity.BCmatching;
import com.jylove.mpc.mpc.entity.BestChoice;
import com.jylove.mpc.mpc.entity.BestChoiceImg;
import com.jylove.mpc.mpc.repository.AdAcPageRepository;
import com.jylove.mpc.mpc.repository.BCmatchingRepository;
import com.jylove.mpc.mpc.repository.BestChoiceImgRepository;
import com.jylove.mpc.mpc.repository.BestChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdacService {

    @Value("${adacVideoLocation}")
    private String adacVideoLocation;
    private final FileService fileService;
    private final AdacImgService adacImgService;
    private final AdAcPageService adAcPageService;
    private final AdAcPageRepository adAcPageRepository;
    private final BestChoiceRepository bestChoiceRepository;
    private final BestChoiceImgRepository bestChoiceImgRepository;
    private final BCmatchingRepository bCmatchingRepository;

    public List<AdAcPageDto> getAdacPage(Page<BestChoice> bestChoices){ //관리자 유기견 관리페이지에서 유기센터 정보 가져오기
        List<AdAcPageDto> adAcPageDtoList = new ArrayList<>();

        for(int i=0; i<bestChoices.getContent().size(); i++){
            adAcPageDtoList.add(AdAcPageDto.of(adAcPageService.getAdacPage(bestChoices.getContent().get(i).getAdAcPage().getId()).orElseThrow(EntityNotFoundException::new)));
        }

        return adAcPageDtoList;
    }

    @Transactional(readOnly = true) //데이터베이스에서 데이터 추가, 수정, 삭제, 조회 - 오류가 발생할 경우 오류 발생 이전상태로 돌릴수 있다.
    public Page<BestChoice> getAdminAdacPage(AdacSearchDto adacSearchDto, Pageable pageable){
        return bestChoiceRepository.getAdminAdacPage(adacSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public AdacFormDto getAdacDtl(Long adacId){
        List<BestChoiceImg> bestChoiceImgList = bestChoiceImgRepository.findByBestChoiceIdOrderByIdAsc(adacId);
        List<AdacImgDto> adacImgDtoList = new ArrayList<>();

        for(BestChoiceImg bestChoiceImg : bestChoiceImgList){ //수정하고자 하는 bestChoice에 해당하는 이미지를 DTO로 변환
            AdacImgDto adacImgDto = AdacImgDto.of(bestChoiceImg);
            adacImgDtoList.add(adacImgDto);
        }

        BestChoice bestChoice = bestChoiceRepository.findById(adacId).orElseThrow(EntityNotFoundException::new);
        AdacFormDto adacFormDto = AdacFormDto.of(bestChoice);
        adacFormDto.setAdacImgDtoList(adacImgDtoList);

        return adacFormDto;
    }

    public Long saveAdac(AdacFormDto adacFormDto, MultipartFile adacVideoFile, List<MultipartFile> adacImgFileList) throws Exception{
        String oriVideoName = adacVideoFile.getOriginalFilename();
        String videoName = "";
        String videoUrl = "";

        //등록할 영상이 있다면
        if(!adacVideoFile.isEmpty()){
            videoName = fileService.uploadFile(adacVideoLocation, oriVideoName, adacVideoFile.getBytes()); //영상 저장 메서드
            videoUrl = "/images/adac/video/" + videoName;
        }

        AdAcPage adAcPage = adAcPageService.getAdacPage(adacFormDto.getAdAcPageDtoId()).orElseThrow(EntityNotFoundException::new);
        BestChoice bestChoice = adacFormDto.createBestChoice();
        bestChoice.updateBestChoice(oriVideoName, videoName, videoUrl);
        bestChoice.setAdAcPage(adAcPage);
        bestChoiceRepository.save(bestChoice);

        for(int i=0; i<adacImgFileList.size(); i++){
            BestChoiceImg bestChoiceImg = new BestChoiceImg();
            bestChoiceImg.setBestChoice(bestChoice);

            if(i == 0){ //첫번째 등록한 이미지를 대표이미지로 사용
                bestChoiceImg.setMainImgYn("Y");
            }
            else{
                bestChoiceImg.setMainImgYn("N");
            }

            adacImgService.saveAdacImg(bestChoiceImg, adacImgFileList.get(i));
        }

        return bestChoice.getId();
    }

    public Long updateAdac(AdacFormDto adacFormDto, MultipartFile adacVideoFile, List<MultipartFile> adacImgFileList) throws Exception{
        //영속 상태
        BestChoice bestChoice = bestChoiceRepository.findById(adacFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        AdAcPage acPage = adAcPageRepository.findById(adacFormDto.getAdAcPageDtoId()).orElseThrow(EntityNotFoundException::new);
        List<Long> adacImgIds = adacFormDto.getAdacImgIds(); //이미지의 bestChoice id값

        //수정할 영상이 있다면
        if(!adacVideoFile.isEmpty()){
            //저장된 영상이 있다면 삭제
            if(!StringUtils.isEmpty(bestChoice.getOriVideoName())){
                fileService.deleteFile(adacVideoLocation+"/"+bestChoice.getVideoName());
            }

            String oriVideoName = adacVideoFile.getOriginalFilename(); //업로드 된 영상의 파일명
            String videoName = fileService.uploadFile(adacVideoLocation, oriVideoName, adacVideoFile.getBytes()); //파일 저장 메서드
            String videoUrl = "/images/adac/video/" + videoName;

            adacFormDto.setOriVideoName(oriVideoName);
            adacFormDto.setVideoName(videoName);
            adacFormDto.setVideoUrl(videoUrl);
        }
        else{ //수정할 영상이 없다면 기존 영상 보존
            adacFormDto.setOriVideoName(bestChoice.getOriVideoName());
            adacFormDto.setVideoName(bestChoice.getVideoName());
            adacFormDto.setVideoUrl(bestChoice.getVideoUrl());
        }

        bestChoice.updateBestChoice(adacFormDto);
        bestChoice.setAdAcPage(acPage);

        for(int i=0; i<adacImgFileList.size(); i++){
            adacImgService.updateAdacImg(adacImgIds.get(i), adacImgFileList.get(i));
        }

        return bestChoice.getId();
    }

    public void deleteAdac(Long adacId){
        bestChoiceImgRepository.deleteByBestChoiceId(adacId);
        bestChoiceRepository.deleteById(adacId);
    }
}
