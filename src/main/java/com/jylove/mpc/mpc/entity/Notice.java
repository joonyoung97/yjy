package com.jylove.mpc.mpc.entity;

import com.jylove.mpc.mpc.dto.NoticeFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class) //엔티티 시간자동저장을 위해 추가
public class Notice {
    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //공지사항 제목

    private String bcOriImgName; //원본 이미지 이름

    private String bcImgName; //변경된 이미지 이름

    private String bcImgUrl; //이미지 경로

    @Column(columnDefinition = "TEXT", nullable = false)
    private String bcText; //내용

    @LastModifiedDate //엔티티가 생성되어 값이 변경될때 시간 자동 저장
    private LocalDateTime regDate; //등록시간

    public Notice createNotice(NoticeFormDto noticeFormDto, String bcOriImgName, String bcImgName, String bcImgUrl){
        Notice notice = new Notice();
        notice.setTitle(noticeFormDto.getTitle());
        notice.setBcOriImgName(bcOriImgName);
        notice.setBcImgName(bcImgName);
        notice.setBcImgUrl(bcImgUrl);
        notice.setBcText(noticeFormDto.getBcText());

        return notice;
    }

    public void updateNotice(NoticeFormDto noticeFormDto){
        this.id = noticeFormDto.getId();
        this.title = noticeFormDto.getTitle();
        this.bcOriImgName = noticeFormDto.getBcOriImgName();
        this.bcImgName = noticeFormDto.getBcImgName();
        this.bcImgUrl = noticeFormDto.getBcImgUrl();
        this.bcText = noticeFormDto.getBcText();
    }
}
