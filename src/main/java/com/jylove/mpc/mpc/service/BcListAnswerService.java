package com.jylove.mpc.mpc.service;

import com.jylove.mpc.mpc.dto.BcListAnswerDto;
import com.jylove.mpc.mpc.entity.BcList;
import com.jylove.mpc.mpc.entity.BcListAnswer;
import com.jylove.mpc.mpc.repository.BcListAnswerRepository;
import com.jylove.mpc.mpc.repository.BcListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class BcListAnswerService {

    private final BcListRepository bcListRepository;
    private final BcListAnswerRepository bcListAnswerRepository;


//    @Transactional(readOnly = true)
//    public Page<BcListAnswer> getBcListAnswerPage(Pageable pageable) {
//        return bcListAnswerRepository.getBcListAnswerPage(pageable);
//    }

    @Transactional(readOnly = true)
    public BcListAnswerDto getBcListAnswerDtl(Long bcListId){
        BcListAnswer bcListAnswer = bcListAnswerRepository.findByBcListId(bcListId);
        BcListAnswerDto bcListAnswerDto = BcListAnswerDto.of(bcListAnswer);
        return bcListAnswerDto;
    }

    public BcListAnswerDto createBcListAnswerDto(){
        return new BcListAnswerDto();
    }

    public void saveBcListAnswer(BcList bcList){
        BcListAnswer bcListAnswer = new BcListAnswer();
        bcListAnswer.setBcList(bcList);
        bcListAnswer.setAdminEmail("");
        bcListAnswer.setAdminTitle("");
        bcListAnswer.setAdminText("");
        bcListAnswerRepository.save(bcListAnswer);
    }

    public Long saveBcListAnswer(Long bcListId, BcListAnswerDto bcListAnswerDto){
        BcList bcList = bcListRepository.findById(bcListId).orElseThrow(EntityNotFoundException::new);
        bcList.setBcAnswerCk(true); //답변완료 상태로 저장

        BcListAnswer bcListAnswer = bcListAnswerRepository.findByBcListId(bcListId);
        bcListAnswer.updateBcListAnswer(bcListAnswerDto); //답변 저장

        return bcListAnswer.getId();
    }
}
