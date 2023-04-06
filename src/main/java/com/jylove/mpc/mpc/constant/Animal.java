package com.jylove.mpc.mpc.constant;

public enum Animal {
    DOG("강아지"), CAT("고양이");

    private final String description;
    Animal(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
