package com.example.usercontrol.enums;

import java.util.Objects;

public enum RoleEnum {
    ADMIN("admin"),
    USER("user"),
    ;


    private final String description;

    RoleEnum(String description) {
        this.description = description;
    }

    public String value() {
        return description;
    }

    public static boolean checkRole(String tar) {
        RoleEnum[] values = RoleEnum.values();
        for (RoleEnum value : values) {
            if (Objects.equals(value.value(), tar)) {
                return true;
            }
        }
        return false;
    }
}
