package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.entity.BcList;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BcListDto {   // 문의내역

    private Long id;

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotBlank(message = "제목을 입력해주세요.")
    private String bcTitle;

    private String bcOriImgName; //원본 이미지 이름

    private String bcImgName; //변경된 이미지 이름

    private String bcImgUrl; //이미지 경로

    @NotBlank(message = "내용을 입력해주세요")
    private String bcText; //내용

    private Boolean bcSecretText;

    private Boolean bcAnswerCk; //답변여부 확인
    private static ModelMapper modelMapper = new ModelMapper();


    public BcList createBcList(){
        return modelMapper.map(this, BcList.class);
    }


    public static BcListDto of(BcList bcList){
        return modelMapper.map(bcList,BcListDto.class);
    }

}
