package com.jylove.mpc.mpc.entity;

import com.jylove.mpc.mpc.dto.AdAcPageDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AdAcPage")
@Getter
@Setter
public class AdAcPage {  // 유기견 관리 센터 정보 테이블
    // 실체, 객체로써 데이터베이스에 정보를 저장하고 관리

    @Id
    @Column(name = "AdAcPage_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String tel;

    private String homepage;

    private String representative;

    private String representativeHp;

    private String addr;

    private String area;

    public static AdAcPage create(AdAcPageDto adAcPageDto) {
        AdAcPage adAcPage = new AdAcPage();
        adAcPage.setName(adAcPageDto.getName());
        adAcPage.setEmail(adAcPageDto.getEmail());
        adAcPage.setTel(adAcPageDto.getTel());
        adAcPage.setHomepage(adAcPageDto.getHomepage());
        adAcPage.setRepresentative(adAcPageDto.getRepresentative());
        adAcPage.setRepresentativeHp(adAcPageDto.getRepresentativeHp());
        adAcPage.setAddr(adAcPageDto.getAddr());
        adAcPage.setArea(adAcPageDto.getArea());
        return adAcPage;
    }

    public void updateAdAcPage(AdAcPageDto adAcPageDto){

        this.name = adAcPageDto.getName();
        this.email = adAcPageDto.getEmail();
        this.tel = adAcPageDto.getTel();
        this.homepage = adAcPageDto.getHomepage();
        this.representative = adAcPageDto.getRepresentative();
        this.representativeHp = adAcPageDto.getRepresentativeHp();
        this.addr = adAcPageDto.getAddr();
        this.area = adAcPageDto.getArea();

    }

}