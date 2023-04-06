package com.jylove.mpc.mpc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdAcPageSearchDto { // 검색

    private String searchBy; // 상품 조회 유형 - 상품명, 상품 등록자 등등
    private String searchQuery=""; // 데이터베이스 조회조건
}
