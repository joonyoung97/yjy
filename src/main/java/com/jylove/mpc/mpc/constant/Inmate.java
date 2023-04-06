package com.jylove.mpc.mpc.constant;

public enum Inmate {
    YES("유"), NO("무");

    private final String description;

    Inmate(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
