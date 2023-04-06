package com.jylove.mpc.mpc.constant;

public enum Age {
    ONEMONTH("1개월"), TWOMONTH("2개월"), THREEMONTH("3개월"), FOURMONTH("4개월"),
    FIVEMONTH("5개월"), SIXMONTH("6개월"), SEVENMONTH("7개월"), EIGHTMONTH("8개월"),
    NINEMONTH("9개월"), TENMONTH("10개월"), ELEVENMONTH("11개월"),
    ONEYEAR("1살"), TWOYEAR("2살"), THREEYEAR("3살"), FOURYEAR("4살"),
    FIVEYEAR("5살"), SIXYEAR("6살"), SEVENYEAR("7살"), EIGHTYEAR("8살"),
    NINEYEAR("9살"), TENYEAR("10살");

    private final String description;
    Age(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
