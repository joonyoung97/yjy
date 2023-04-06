package com.jylove.mpc.mpc.constant;

public enum ParentingExperience {
    ONEtoFour_Months("1달~4달"), FIVEtoEIGHT_Months("5달~8달"),
    ONEYEAR("1년이상"), TWOYEARS("2년이상"), THREEYEARS("3년이상");
    private final String description;

    ParentingExperience(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
