package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.CompanionAnimalDto;
import com.jylove.mpc.mpc.dto.MemberFormDto;
import com.jylove.mpc.mpc.entity.CompanionAnimal;
import com.jylove.mpc.mpc.entity.Member;
import com.jylove.mpc.mpc.repository.CompanionAnimalRepository;
import com.jylove.mpc.mpc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional // 서비스클래스가 동작하다가 에러가 발생하면 에러 발생 이전상태로 돌아가게 하기위한것
@RequiredArgsConstructor // Final 이나  notnull 이 있는 객체에 생성자를 생성하여 넣어주는 것
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final CompanionAnimalRepository companionAnimalRepository;
    private final SendEmailService sendEmailService;

    public List<MemberFormDto> memberFormDtos(Page<Member> members) {
        List<MemberFormDto> memberFormDtoList = new ArrayList<>();

        for(int i=0; i<members.getContent().size(); i++){
            CompanionAnimal companionAnimals = companionAnimalRepository.findByMemberId(members.getContent().get(i).getId());
            CompanionAnimalDto companionAnimalDto = CompanionAnimalDto.of(companionAnimals);

            Member member = memberRepository.findById(members.getContent().get(i).getId()).orElseThrow(EntityNotFoundException::new);
            MemberFormDto memberFormDto = MemberFormDto.of(member);
            memberFormDto.setCompanionAnimals(companionAnimalDto);

            memberFormDtoList.add(memberFormDto);
        }

        return memberFormDtoList;
    }

    public void saveCompanionAnimal(MemberFormDto memberFormDto,Member member) {
        CompanionAnimal companionAnimal = new CompanionAnimal();
        companionAnimal.setCompanionChoice(memberFormDto.getCompanionChoice());
        companionAnimal.setMember(member);
        companionAnimalRepository.save(companionAnimal);
        Long companionAnimalId = companionAnimal.getId();
    }
    public Member saveMember(Member member, MemberFormDto memberFormDto){  //회원가입시 데이터베이스에 저장하기 위한 메서드
        validateEmail(member);
        saveCompanionAnimal(memberFormDto,member);

        return memberRepository.save(member);  // JPA를 통해서 insert into 실행
    }

    private void validateEmail(Member member) {
        Member find = memberRepository.findByEmail(member.getEmail());
        if(find != null){
            throw  new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null){  // 이메일 조회 실패
            throw new UsernameNotFoundException(email);
        }

        return  User.builder().username(member.getEmail())
                .password(member.getPassword()).roles(member.getRole().toString()).build();
        // 로그인 하려는 유저 객체 넘기기 (이메일이 존재 한다면 )
    }
    @Transactional(readOnly = true) // 마이페이지 사용자 정보
    public MemberFormDto getUserDtl(String email) {
        Member member = memberRepository.findByEmail(email);
        MemberFormDto memberFormDto = MemberFormDto.of(member);
        return memberFormDto;
    }

    public Long memberUpdate(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());
        member.memberUpdate(memberFormDto, passwordEncoder);
        return memberFormDto.getId();
        // 마이페이지 사용자 수정
    }

    @Transactional // 회원탈퇴
    public void memberDelete(Long id) {
        companionAnimalRepository.deleteByMemberId(id);
        memberRepository.deleteById(id);
    }

    public String findId(String name, String tel){ // 아이디찾기
        Member member = memberRepository.findByNameAndTel(name,tel);
        return member.getEmail();
    }

    private String getTempPassword() { // 임시비밀번호 랜덤 생성
        String t = "";
        for (int i = 0; i < 8; i++) { // 0부터 8자리
            int s = (int) (Math.random() * 3);
            if (s == 0)
                t += Character.toString((char) (Math.random() * 10) + 48); // 숫자
            if (s == 1)
                t += Character.toString((char) (Math.random() * 26) + 65); // 알파벳 대문자
            if (s == 2)
                t += Character.toString((char) (Math.random() * 26) + 97); // 알파벳 소문자
        }
        return t;
    }
    public void sendEmailAndPw(String email, PasswordEncoder passwordEncoder) {
        String temppassword = getTempPassword();
        sendEmailService.sendMail(email,temppassword);
        Member member = memberRepository.findByEmail(email);
        MemberFormDto memberFormDto = MemberFormDto.of(member);
        memberFormDto.setPassword(temppassword);
        member.memberUpdate(memberFormDto, passwordEncoder);
        // 메일로 받은 임시비밀번호로 로그인,변경 가능
    }

    public Long findemail(String email){
        Member member = memberRepository.findByEmail(email);

        return member.getId();
    }
}