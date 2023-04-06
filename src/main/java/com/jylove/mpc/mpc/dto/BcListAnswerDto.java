package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.entity.BcList;
import com.jylove.mpc.mpc.entity.BcListAnswer;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BcListAnswerDto {  // 문의내역 답변 테이블

    private Long id;

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String adminEmail;

    @NotBlank(message = "제목을 입력해주세요.")
    private String adminTitle;

    @NotBlank(message = "내용을 입력해주세요")
    private String adminText; //내용

    private BcList bcList;

    private static ModelMapper modelMapper = new ModelMapper();

    public BcListAnswer createBcListAnswer(){
        return modelMapper.map(this, BcListAnswer.class);
    }

    public static BcListAnswerDto of(BcListAnswer bcListAnswer){
        return modelMapper.map(bcListAnswer,BcListAnswerDto.class);
    }

}
