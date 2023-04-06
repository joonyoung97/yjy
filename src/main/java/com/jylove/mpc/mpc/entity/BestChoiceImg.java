package com.jylove.mpc.mpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bestChoiceImg")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class) //엔티티 시간자동저장을 위해 추가
public class BestChoiceImg {
    @Id
    @Column(name = "BestChoiceImg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oriImgName; //원본 이미지이름

    private String imgName; //변경된 이미지이름

    private String imgUrl; //이미지 경로

    private String mainImgYn; //대표이미지 여부

    @CreatedDate //엔티티가 생성되어 저장될때 시간을 자동으로 저장
    @Column(updatable = false)
    private LocalDateTime regTime;
    //column -> updatable false : update 쿼리 할때는 데이터 변경 막음
    //          insertable false : insert 쿼리 할때 막음

    @LastModifiedDate //엔티티의 값을 변경할때 시간을 자동으로 저장
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "BestChoice_id")
    private BestChoice bestChoice;

    public void updateBestChoiceImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
