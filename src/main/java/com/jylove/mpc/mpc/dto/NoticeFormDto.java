package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.entity.Notice;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NoticeFormDto {
    //공지사항 등록하기 위한 Dto

    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title; //공지사항 제목

    private String bcOriImgName; //원본 이미지 이름

    private String bcImgName; //변경된 이미지 이름

    private String bcImgUrl; //이미지 경로

    @NotBlank(message = "내용을 입력해주세요")
    private String bcText; //내용

    private static ModelMapper modelMapper = new ModelMapper();

    public Notice createNotice(){
        return modelMapper.map(this, Notice.class);
    }

    public static NoticeFormDto of(Notice notice){
        return modelMapper.map(notice, NoticeFormDto.class);
    }
}
