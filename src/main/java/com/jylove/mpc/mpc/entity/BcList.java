package com.jylove.mpc.mpc.entity;

import com.jylove.mpc.mpc.dto.BcListDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "bcList")
@Getter
@Setter
@ToString
//@EntityListeners(AuditingEntityListener.class) //엔티티 시간자동저장을 위해 추가
public class BcList extends BaseTimeEntity { // 문의내역 테이블

    @Id
    @Column(name = "bcList_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;   // 작성자

    private String bcTitle;   // 문의내역 제목

    private String bcOriImgName; //원본 이미지 이름

    private String bcImgName; //변경된 이미지 이름

    private String bcImgUrl; //이미지 경로

    @Column(columnDefinition = "TEXT", nullable = false)
    private String bcText; //내용

    private Boolean bcSecretText;
    // 비밀글, 공개글?  체크박스로 해서 체크 관리자는 다 보게되며, 비밀글일 경우에는 잠금 표시가 보여지게 되어 아예 a태그를 빼어 클릭조차 되지 않게하여 보지 못하게 표시 해야함

//    @LastModifiedDate //엔티티가 생성되어 저장될때 시간 자동 저장
//    private LocalDateTime regDate; //등록시간

    private Boolean bcAnswerCk; //답변여부 확인

    public BcList createBcList(BcListDto bcListDto, String bcOriImgName, String bcImgName, String bcImgUrl){
        BcList bcList = new BcList();
        bcList.setEmail(bcListDto.getEmail());
        bcList.setBcTitle(bcListDto.getBcTitle());
        bcList.setBcOriImgName(bcOriImgName);
        bcList.setBcImgName(bcImgName);
        bcList.setBcImgUrl(bcImgUrl);
        bcList.setBcText(bcListDto.getBcText());
        bcList.setBcSecretText(bcListDto.getBcSecretText());
        bcList.setBcAnswerCk(false);
        return bcList;
    }


    public void updateBcList(BcListDto bcListDto){
        this.id = bcListDto.getId();
        this.email = bcListDto.getEmail();
        this.bcTitle = bcListDto.getBcTitle();
        this.bcOriImgName = bcListDto.getBcOriImgName();
        this.bcImgName = bcListDto.getBcImgName();
        this.bcImgUrl = bcListDto.getBcImgUrl();
        this.bcText = bcListDto.getBcText();
        this.bcSecretText = bcListDto.getBcSecretText();
    }
}
