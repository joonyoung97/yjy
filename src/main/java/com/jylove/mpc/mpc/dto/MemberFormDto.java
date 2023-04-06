package com.jylove.mpc.mpc.dto;

import com.jylove.mpc.mpc.constant.*;
import com.jylove.mpc.mpc.entity.CompanionAnimal;
import com.jylove.mpc.mpc.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberFormDto {
    // 계층간 데이터 교환을 위해 사용하는 객체
    // dto -> entity -> repository -> service -> control
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;
    @NotNull(message = "생년월일은 필수 입력 값 입니다.")
    private String  brith;
    @NotEmpty(message = "이메일은 필수 입력 값 입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 입력 값 입니다.")
    @Length(min=8, max=12, message = "비밀번호는 8자 이상 12자 이하로 입력하세요.")
    private String password;
    @NotEmpty(message = "전화번호는 필수 입력 값 입니다.")
    @Length(max = 13, message = "전화번호는 13자 이하로 입력하세요.")
    private String tel;
    @NotEmpty(message = "주소는 필수 입력 값 입니다.")
    private String address;
    @NotNull(message = "MBTI는 필수 입력 값 입니다.")
    private Mbti mbti;
    @NotNull(message = "양육경험은 필수 입력 값 입니다.")
    private ParentingExperience parentingExperience;
    @NotNull(message = "출퇴근시간은 필수 입력 값 입니다.")
    private String workTime;
    @NotNull(message = "동거인 유/무는 필수 입력 값 입니다.")
    private Inmate inmate;
    @NotNull(message = "관심동물은 필수 입력 값 입니다.")
    private String companionChoice;
    private CompanionAnimalDto companionAnimals;

    private static ModelMapper modelMapper = new ModelMapper();
    public static MemberFormDto of(Member member){
        return modelMapper.map(member, MemberFormDto.class);
    }
}