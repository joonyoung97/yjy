package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.AdAcPageDto;
import com.jylove.mpc.mpc.dto.AdAcPageSearchDto;
import com.jylove.mpc.mpc.entity.AdAcPage;
import com.jylove.mpc.mpc.entity.BCmatching;
import com.jylove.mpc.mpc.repository.AdAcPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional  // 서비스클래스가 동작하다가 에러가 발생하면 에러 발생 이전상태로 돌아가게 하기위한것
@RequiredArgsConstructor  // Final 이나  notnull 이 있는 객체에 생성자를 생성하여 넣어주는 것
public class AdAcPageService {

    private final AdAcPageRepository adAcPageRepository;


    // 센터 삭제하기
    public void deleteAdAcPage(Long AdAcPageId) {
        adAcPageRepository.deleteById(AdAcPageId);
    }

    public List<AdAcPageDto> getAdacPageMatching(Page<BCmatching> bCmatchings){
        List<AdAcPageDto> list = new ArrayList<>();

        for(int i=0; i<bCmatchings.getContent().size(); i++){
            AdAcPage adAcPage = adAcPageRepository.findById(bCmatchings.getContent().get(i).getAdAcPageId()).orElseThrow(EntityNotFoundException::new);
            list.add(AdAcPageDto.of(adAcPage));
        }

        return list;
    }

    // 전체 조회하기
    public List<AdAcPageDto> getAdacPageAll(){
        List<AdAcPage> list = adAcPageRepository.findAll();
        List<AdAcPageDto> adAcPageDtos = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            adAcPageDtos.add(i, AdAcPageDto.of(list.get(i)));
        }

        return adAcPageDtos;
    }

    // 센터리스트 하나 조회하기
    public Optional<AdAcPage> getAdacPage(Long id){
        return adAcPageRepository.findById(id);
    }

    // 상품 수정하기

    public Long updateAdacpage(AdAcPageDto adAcPageDto) throws Exception{
        //영속 상태
        AdAcPage adAcPage = adAcPageRepository.findById(adAcPageDto.getId()).orElseThrow(EntityNotFoundException::new);
        //상품 수정
        adAcPage.updateAdAcPage(adAcPageDto); // 상품 등록 화면으로부터 전달 받은 adAcPageDto을 통해서 상품 엔티티를 업데이트 합니다.

        return adAcPage.getId();

    }




    //수정하기를 위한 상세페이지 불러오기
    @Transactional(readOnly = true)
    public AdAcPageDto getAdAcPageDetail(Long AdAcPageId) {

        AdAcPage adAcPage = adAcPageRepository.findById(AdAcPageId).orElseThrow(EntityNotFoundException::new);
        AdAcPageDto adAcPageDto = AdAcPageDto.of(adAcPage);

        return adAcPageDto;
    }



    // 검색창
    @Transactional(readOnly = true) // 데이터추가,수정,삭제,조회 - 오류가 발생할경우 오류발생 이전 상태로 돌릴수 있다.
    public Page<AdAcPage> getAdminItemPage(AdAcPageSearchDto adAcPageSearchDto, Pageable pageable) {  // 검색
        return adAcPageRepository.getItem(pageable, adAcPageSearchDto);
    }


    @Transactional(readOnly = true)
    public Page<AdAcPage> getItem(Pageable pageable, AdAcPageSearchDto adAcPageSearchDto) {
        return adAcPageRepository.getItem(pageable, adAcPageSearchDto);  // 검색
    }





    public AdAcPage saveadacpage(AdAcPage adAcPage) {  // 유기견 관리 센터 등록시 데이터베이스에 저장하기 위한 메서드
        duplication(adAcPage);

        return (AdAcPage) adAcPageRepository.save(adAcPage); // JPA를 통해서 insert into 실행
    }

    private void duplication(AdAcPage adAcPage) {
        AdAcPage temp = adAcPageRepository.findByName(adAcPage.getName());
        if (temp != null) {
            throw new IllegalStateException("이미 등록된 유기견 관리 센터입니다.");
        }
    }
}
