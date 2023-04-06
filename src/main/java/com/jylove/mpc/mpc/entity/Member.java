package com.jylove.mpc.mpc.entity;

import com.jylove.mpc.mpc.constant.*;
import com.jylove.mpc.mpc.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member  {
    // 실체, 객체로써 데이터베이스에 정보를 저장하고 관리
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String brith;
    @Column(unique = true)
    private String email;
    private String password;
    private String tel;
    private String address;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    @Enumerated(EnumType.STRING)
    private ParentingExperience parentingExperience;
    private String workTime;
    @Enumerated(EnumType.STRING)
    private Inmate inmate;
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setBrith(memberFormDto.getBrith());
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setTel(memberFormDto.getTel());
        member.setAddress(memberFormDto.getAddress());
        member.setMbti(memberFormDto.getMbti());
        member.setParentingExperience(memberFormDto.getParentingExperience());
        member.setWorkTime(memberFormDto.getWorkTime());
        member.setInmate(memberFormDto.getInmate());
        member.setRole(Role.USER);
        return member;
    }
    public void memberUpdate(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        this.password = password;
        this.tel = memberFormDto.getTel();
        this.address = memberFormDto.getAddress();
        this.mbti = memberFormDto.getMbti();
        this.parentingExperience = memberFormDto.getParentingExperience();
        this.workTime = memberFormDto.getWorkTime();
        this.inmate = memberFormDto.getInmate();
    }

    public void updateMemberMange(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        this.name = memberFormDto.getName();
        this.brith = memberFormDto.getBrith();
        this.email = memberFormDto.getEmail();
        this.password = password;
        this.tel = memberFormDto.getTel();
        this.address = memberFormDto.getAddress();
        this.mbti = memberFormDto.getMbti();
        this.parentingExperience = memberFormDto.getParentingExperience();
        this.workTime = memberFormDto.getWorkTime();
        this.inmate = memberFormDto.getInmate();
    }
}
