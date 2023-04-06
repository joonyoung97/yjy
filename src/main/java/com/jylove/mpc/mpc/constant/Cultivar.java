package com.jylove.mpc.mpc.constant;

public enum Cultivar {
    GOLDEN_RETRIEVER("골든리트리버"), DACHSHUND("닥스훈트"), LABRADOR_DOG("래브라도 리트리버"),
    MALTESE("몰티즈"), MINIATURE_SCHNAUZER("미니어처 슈나우저"), MINIATURE_POODLE("미니어처 푸들"),
    MINIATURE_PINSCHER("미니어처 핀셔"), BEDLINGTON_TERRIER("베들링턴 테리어"), BORDER_COLLIE("보더콜리"),
    BOSTON_TERRIER("보스턴 테리어"), BEAGLE("비글"), BICHON_FRIZE("비숑 프리제"), SAMOYED("사모예드"),
    SHETLAND_SHEEPDOG("셔틀랜드 시프도그"), STANDARD_POODLE("스탠다드 푸들"), SHIBA_INU("시바 이누"),
    SIBERIAN_HUSKY("시베리안 허스키"), SHIH_TZU("시츄"), YORKSHIRE_TERRIER("요크셔테리어"),
    WELSH_CORGI("웰시코기"), JAPANESE_SPITZ("제페니즈 스피츠"), JINDO_DOG("진돗개"),
    CHIHUAHUA("치와와"), PUG("퍼그"), PEKINGESE("페키니즈"), POMERANIAN("포메라니안"),
    POODLE("푸들"), MIX("믹스견"),
    SCOTTISH_FOLD("스코티시 폴드"), SIAMESE_CAT("샴"), PERSIAN("페르시안"),
    TURKISH_ANGORA("터키시 앙고라"), RUSSIAN_BLUE("러시안 블루"), BENGAL("벵갈"),
    MUNCHKIN("먼치킨"), ABYSSINIAN("아비시니안"), NORWEGIAN_FOREST_CAT("노르웨이숲"),
    RAGDOLL("랙돌"), SPHINX("스핑크스"), KOREAN_SHORT_HAIR("코리안 숏헤어");

    private final String description;
    Cultivar(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
