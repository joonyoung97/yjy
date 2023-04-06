package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.entity.BestChoiceImg;
import com.jylove.mpc.mpc.repository.BestChoiceImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class AdacImgService {
    @Value("${adacImgLocation}")
    private String adacImgLocation;

    private final FileService fileService;
    private final BestChoiceImgRepository bestChoiceImgRepository;

    public Long saveAdacImg(BestChoiceImg bestChoiceImg, MultipartFile adacImgFile) throws Exception{
        String oriImgName = adacImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //등록할 이미지가 있다면
        if(!adacImgFile.isEmpty()){
            imgName = fileService.uploadFile(adacImgLocation, oriImgName, adacImgFile.getBytes());
            imgUrl = "/images/adac/image/" + imgName;
        }

        bestChoiceImg.updateBestChoiceImg(oriImgName, imgName, imgUrl);
        bestChoiceImgRepository.save(bestChoiceImg);

        return bestChoiceImg.getId();
    }

    public Long updateAdacImg(Long bestChoiceImgId, MultipartFile adacImgFile) throws Exception{
        //영속 상태
        BestChoiceImg bestChoiceImg = bestChoiceImgRepository.findById(bestChoiceImgId).orElseThrow(EntityNotFoundException::new);

        //등록할 이미지가 있다면
        if(!adacImgFile.isEmpty()){
            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(bestChoiceImg.getOriImgName())){
                fileService.deleteFile(adacImgLocation+"/"+bestChoiceImg.getImgName());
            }

            String oriImgName = adacImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(adacImgLocation, oriImgName, adacImgFile.getBytes());
            String imgUrl = "/images/adac/image/" + imgName;

            bestChoiceImg.updateBestChoiceImg(oriImgName, imgName, imgUrl);
        }
        else{
            bestChoiceImg.updateBestChoiceImg(bestChoiceImg.getOriImgName(), bestChoiceImg.getImgName(), bestChoiceImg.getImgUrl());
        }

        return bestChoiceImg.getId();
    }
}
