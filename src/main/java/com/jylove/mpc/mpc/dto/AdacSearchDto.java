package com.jylove.mpc.mpc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdacSearchDto {
    private String searchAdacPage; //센터 조회

    private String searchAnimal; //강아지,고양이 조회

    private String searchCultivar; //품종 조회
}
