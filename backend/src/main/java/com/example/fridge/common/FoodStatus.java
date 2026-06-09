package com.example.fridge.common;

public enum FoodStatus {
    NORMAL("NORMAL", "正常"),
    EXPIRING_SOON("EXPIRING_SOON", "即将过期"),
    EXPIRED("EXPIRED", "已过期");

    private final String code;
    private final String text;

    FoodStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
