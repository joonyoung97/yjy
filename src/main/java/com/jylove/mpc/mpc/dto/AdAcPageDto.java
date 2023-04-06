package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.entity.AdAcPage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AdAcPageDto { // 유기견 관리 센터 저장하여 넘기는 DTO
    // 계층간 데이터 교환을 위해 사용하는 객체
    // dto -> entity -> repository -> service -> control

    private Long id;

    @NotBlank(message = "유기견 관리 센터 정보는 필수 입력값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
    private String tel;

    @NotEmpty(message = "홈페이지는 필수 입력 값입니다.")
    private String homepage;

    private String representative;
    private String representativeHp;
    private String addr;
    private String area;


    private static ModelMapper modelMapper = new ModelMapper();

    public AdAcPage createAdAcPage(){
        return modelMapper.map(this,AdAcPage.class);
    }

    public static AdAcPageDto of(AdAcPage adAcPage_obj){
        return modelMapper.map(adAcPage_obj,AdAcPageDto.class);
    }
}
