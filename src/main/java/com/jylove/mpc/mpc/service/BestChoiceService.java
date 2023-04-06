package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.AdAcPageDto;
import com.jylove.mpc.mpc.dto.AdacFormDto;
import com.jylove.mpc.mpc.dto.AdacImgDto;
import com.jylove.mpc.mpc.dto.BestChoiceDto;
import com.jylove.mpc.mpc.entity.*;
import com.jylove.mpc.mpc.repository.BCmatchingRepository;
import com.jylove.mpc.mpc.repository.BestChoiceImgRepository;
import com.jylove.mpc.mpc.repository.BestChoiceRepository;
import com.jylove.mpc.mpc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class BestChoiceService {

    private final MemberRepository memberRepository;
    private final BestChoiceRepository bestChoiceRepository;
    private final BestChoiceImgRepository bestChoiceImgRepository;
    private final BCmatchingRepository bCmatchingRepository;

    public List<AdacFormDto> randomChoice(String email){ //로그인한 계정의 MBTI와 유기견의 MBTI를 비교해서 같은 유기견을 랜덤으로 추천해주기 위한 메서드
        Member member = memberRepository.findByEmail(email);
        String mbti = String.valueOf(member.getMbti());

        List<BestChoice> bestChoiceList = bestChoiceRepository.findAll();
        List<Long> bcequalsIdList = new ArrayList<>();

        for(int i=0; i<bestChoiceList.size(); i++){
            if(mbti.equals(String.valueOf(bestChoiceList.get(i).getMbti()))){ //bestChoice에 등록된 유기견의 MBTI가 로그인한 회원의 MBTI와 같다면
                bcequalsIdList.add(bestChoiceList.get(i).getId()); //bestChoice에 등록된 유기견의 id값 리스트에 저장
            }
        }

        Collections.shuffle(bcequalsIdList); //등록된 유기견을 랜덤으로 추천해주기 위해 섞어주었다.

        if(bcequalsIdList.size() > 6) { //추천할 유기견을 6마리로 제한하기 위해
            bcequalsIdList = bcequalsIdList.subList(0, 6);
        }
        else{
            bcequalsIdList = bcequalsIdList.subList(0, bcequalsIdList.size());
        }

        List<AdacFormDto> adacFormDtoList = new ArrayList<>();

        for(int i=0; i<bcequalsIdList.size(); i++){ //추천할 유기견 수 만큼 Dto 리스트로 담아주기 위해
            adacFormDtoList.add(getBestChoiceDtl(bcequalsIdList.get(i)));
        }

        return adacFormDtoList;
    }

    @Transactional(readOnly = true)
    public AdacFormDto getBestChoiceDtl(Long bcId){
        List<BestChoiceImg> bestChoiceImgList = bestChoiceImgRepository.findByBestChoiceIdOrderByIdAsc(bcId);
        List<AdacImgDto> adacImgDtoList = new ArrayList<>();

        for(BestChoiceImg bestChoiceImg : bestChoiceImgList){ //수정하고자 하는 bestChoice에 해당하는 이미지를 DTO로 변환
            AdacImgDto adacImgDto = AdacImgDto.of(bestChoiceImg);
            adacImgDtoList.add(adacImgDto);
        }

        BestChoice bestChoice = bestChoiceRepository.findById(bcId).orElseThrow(EntityNotFoundException::new);
        AdacFormDto adacFormDto = AdacFormDto.of(bestChoice);
        adacFormDto.setAdacImgDtoList(adacImgDtoList);

        return adacFormDto;
    }

    public void saveBcMatching(String email, AdacFormDto adacFormDto){
        Member member = memberRepository.findByEmail(email);
        BCmatching bCmatching = new BCmatching();
        bCmatching = bCmatching.createBcmatching(member.getId(), adacFormDto);
        bCmatchingRepository.save(bCmatching);
    }

    public boolean checkMatching(String email, AdacFormDto adacFormDto){
        Member member = memberRepository.findByEmail(email);

        List<BCmatching> bCmatchingList = bCmatchingRepository.findByMemberId(member.getId());

        if(bCmatchingList.isEmpty()){
            return false;
        }
        else{
            for(int i=0; i<bCmatchingList.size(); i++){
                if(bCmatchingList.get(i).getBestChoiceId() == adacFormDto.getId()){
                    return true;
                }
            }
            return false;
        }
    }

    //매칭된 유기센터 가져오기
    public Page<BCmatching> getBcMatchingPage(Long id, Pageable pageable){
        return bestChoiceRepository.getBcMatchingPage(id, pageable);
    }


    //매칭된 유기동물 정보 가져오기
    public List<BestChoiceDto> getBestChoiceMatching(Page<BCmatching> bCmatchings){
        List<BestChoiceDto> list = new ArrayList<>();

        for(int i=0; i<bCmatchings.getContent().size(); i++){
            BestChoice bestChoice = bestChoiceRepository.findById(bCmatchings.getContent().get(i).getBestChoiceId()).orElseThrow(EntityNotFoundException::new);
            list.add(BestChoiceDto.of(bestChoice));
        }

        return list;
    }
}
