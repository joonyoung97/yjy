package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.constant.Mbti;
import com.jylove.mpc.mpc.entity.AdAcPage;
import com.jylove.mpc.mpc.entity.BestChoice;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class BestChoiceDto {
    private Long id;

    private String animal; //동물종류 - 강아지,고양이

    private String cultivar; //품종

    private String oriVideoName; //원본 영상이름

    private String videoName; //변경된 영상이름

    private String videoUrl; //영상 저장경로

    private String nature; //성격

    private String size; //사이즈

    private String vaccination; //예방접종 여부

    private String age; //나이

    private Mbti mbti; //MBTI

    private AdAcPage adAcPage;  //유기견 관리센터

    private static ModelMapper modelMapper = new ModelMapper();

    public BestChoice createBestChoice(){
        return modelMapper.map(this,BestChoice.class);
    }

    public static BestChoiceDto of(BestChoice bestChoice_obj){
        return modelMapper.map(bestChoice_obj, BestChoiceDto.class);
    }
}
