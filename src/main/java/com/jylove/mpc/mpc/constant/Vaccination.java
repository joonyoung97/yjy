package com.jylove.mpc.mpc.constant;

public enum Vaccination {
    YES("접종완료"), NO("접종안됨");

    private final String description;
    Vaccination(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
