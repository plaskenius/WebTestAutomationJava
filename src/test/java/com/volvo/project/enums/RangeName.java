package com.project.project.enums;

/**
 * This class contains dictionary data representing Ranges
 * @author PawelPie
 *
 */

public enum RangeName {

    CONVENTIONAL("Conventional Vehicle"),
    HD_VEHICLE("HD Vehicle");

    private final String value;

    private RangeName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
