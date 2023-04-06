package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.MemberFormDto;
import com.jylove.mpc.mpc.entity.Member;
import com.jylove.mpc.mpc.repository.CompanionAnimalRepository;
import com.jylove.mpc.mpc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class AdMemberManageService {
    private final MemberRepository memberRepository;
    private final CompanionAnimalRepository companionAnimalRepository;

    @Transactional(readOnly = true) //데이터베이스에서 데이터 추가, 수정, 삭제, 조회
    public Page<Member> getMemberMangePage(Pageable pageable){
        return memberRepository.getMemberMangePage(pageable) ;
    }

    @Transactional(readOnly = true) // 수정하기를 위한 상세페이지 불러오기
    public MemberFormDto getMemberMangeDetail(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        MemberFormDto memberFormDto = MemberFormDto.of(member);
        return memberFormDto;
    }

    @Transactional // 회원 수정
    public Long updateMemberMange(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) throws Exception{
        System.out.println("aaaaaa");
        // 영속 상태
        Member member = memberRepository.findById(memberFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        System.out.println("bbbbbb");
        // 상품 수정
        member.updateMemberMange(memberFormDto,passwordEncoder);
        System.out.println("cccccc");
        return member.getId();
    }

    @Transactional  // 회원삭제
    public void deleteMemberManage(Long memberId) {
        companionAnimalRepository.deleteByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }
}
