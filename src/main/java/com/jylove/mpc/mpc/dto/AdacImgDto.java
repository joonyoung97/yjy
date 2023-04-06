package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.entity.BestChoiceImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AdacImgDto {
    private Long id;

    private String oriImgName;

    private String imgName;

    private String imgUrl;

    private String mainImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AdacImgDto of(BestChoiceImg bestChoiceImg){
        return modelMapper.map(bestChoiceImg, AdacImgDto.class);
    }
}
