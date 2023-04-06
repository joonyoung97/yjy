package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.constant.*;
import com.jylove.mpc.mpc.entity.AdAcPage;
import com.jylove.mpc.mpc.entity.BestChoice;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdacFormDto {
    private Long id;

    @NotEmpty(message = "강아지 또는 고양이를 선택해주세요.")
    private String animal;

    @NotEmpty(message = "품종을 선택해주세요.")
    private String cultivar;

    private String oriVideoName;

    private String videoName;

    private String videoUrl;

    @NotEmpty(message = "성격을 선택해주세요.")
    private String nature;

    @NotEmpty(message = "사이즈를 선택해주세요.")
    private String size;

    @NotEmpty(message = "예방접종 여부를 선택해주세요.")
    private String vaccination;

    @NotEmpty(message = "나이를 선택해주세요.")
    private String age;

    @NotNull(message = "MBTI를 선택해주세요.")
    private Mbti mbti;

    @NotNull(message = "등록할 유기견 센터를 선택해주세요.")
    private Long adAcPageDtoId;

    private AdAcPage adAcPage;

    private List<AdacImgDto> adacImgDtoList = new ArrayList<>();

    private List<Long> adacImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public BestChoice createBestChoice(){
        return modelMapper.map(this, BestChoice.class);
    }

    public static AdacFormDto of(BestChoice bestChoice){
        return modelMapper.map(bestChoice, AdacFormDto.class);
    }
}
