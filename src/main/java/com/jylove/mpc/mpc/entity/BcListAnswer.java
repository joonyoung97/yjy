package com.jylove.mpc.mpc.entity;

import com.jylove.mpc.mpc.dto.BcListAnswerDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bcListAnswer")
@Getter
@Setter
public class BcListAnswer {  // 문의내역 답변 테이블

    @Id
    @Column(name = "bcListAnswer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adminEmail;

    private String adminTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String adminText; //내용

    @OneToOne
    @JoinColumn(name = "bcList_id")
    private BcList bcList;

    public BcListAnswer createBcListAnswer(BcListAnswerDto bcListAnswerDto){
        BcListAnswer bcListAnswer = new BcListAnswer();
        bcListAnswer.setAdminEmail(bcListAnswerDto.getAdminEmail());
        bcListAnswer.setAdminTitle(bcListAnswerDto.getAdminTitle());
        bcListAnswer.setAdminText(bcListAnswerDto.getAdminText());

        return bcListAnswer;
    }

    public void updateBcListAnswer(BcListAnswerDto bcListAnswerDto){
        this.adminEmail = bcListAnswerDto.getAdminEmail();
        this.adminTitle = bcListAnswerDto.getAdminTitle();
        this.adminText = bcListAnswerDto.getAdminText();
    }
}
