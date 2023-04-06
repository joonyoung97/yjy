package com.jylove.mpc.mpc.constant;

public enum Nature {
    CONFIDENT("리더쉽"), INDEPENDENT("독립적"), SHY("소심한"),
    HAPPY("활발한"), ADAPTABLE("적응력있는"), SENSITIVITY("예민한");

    private final String description;
    Nature(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
