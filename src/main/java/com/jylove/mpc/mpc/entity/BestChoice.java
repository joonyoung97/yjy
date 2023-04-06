package com.jylove.mpc.mpc.entity;

import com.jylove.mpc.mpc.constant.*;
import com.jylove.mpc.mpc.dto.AdacFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "bestChoice")
@Getter
@Setter
@ToString
public class BestChoice {
    @Id
    @Column(name = "BestChoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String animal; //동물종류 - 강아지,고양이

    private String cultivar; //품종

    private String oriVideoName; //원본 영상이름

    private String videoName; //변경된 영상이름

    private String videoUrl; //영상 저장경로

    private String nature; //성격

    private String size; //사이즈

    private String vaccination; //예방접종 여부

    private String age; //나이

    @Enumerated(EnumType.STRING)
    private Mbti mbti; //MBTI

    @ManyToOne
    @JoinColumn(name = "AdAcPage_id")
    private AdAcPage adAcPage;  //유기견 관리센터

    public void updateBestChoice(String oriVideoName, String videoName, String videoUrl){
        this.oriVideoName = oriVideoName;
        this.videoName = videoName;
        this.videoUrl = videoUrl;
    }

    public void updateBestChoice(AdacFormDto adacFormDto){
        this.animal = adacFormDto.getAnimal();
        this.cultivar = adacFormDto.getCultivar();
        this.oriVideoName = adacFormDto.getOriVideoName();
        this.videoName = adacFormDto.getVideoName();
        this.videoUrl = adacFormDto.getVideoUrl();
        this.nature = adacFormDto.getNature();
        this.size = adacFormDto.getSize();
        this.vaccination = adacFormDto.getVaccination();
        this.age = adacFormDto.getAge();
        this.mbti = adacFormDto.getMbti();
    }
}
