package com.jylove.mpc.mpc.entity;

import com.jylove.mpc.mpc.dto.AdacFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bcmatching")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class) //엔티티 시간자동저장을 위해 추가
public class BCmatching {
    @Id
    @Column(name = "bcmatching_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bestChoiceId;

    private Long memberId;

    private Long adAcPageId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    public BCmatching createBcmatching(Long memberId, AdacFormDto adacFormDto){
        BCmatching bCmatching = new BCmatching();
        bCmatching.setBestChoiceId(adacFormDto.getId());
        bCmatching.setMemberId(memberId);
        bCmatching.setAdAcPageId(adacFormDto.getAdAcPage().getId());

        return bCmatching;
    }
}
