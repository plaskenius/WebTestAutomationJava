package com.project.project.enums;

/**
 * This class contains dictionary data representing Models
 * @author PawelPie
 *
 */

public enum ModelName {

    VHD("VHD (project Trucks)"),
    VN("VN (project Trucks)");

    private final String value;

    private ModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
