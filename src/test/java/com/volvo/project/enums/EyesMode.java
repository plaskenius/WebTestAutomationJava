package com.project.project.enums;

/**
 * Created by a049473 on 2017-10-25.
 */
public enum EyesMode {
    ON("on"), OFF("off");

    private EyesMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    private final String value;
}
