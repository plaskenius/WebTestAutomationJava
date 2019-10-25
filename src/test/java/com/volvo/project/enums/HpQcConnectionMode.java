/*
 * author PawelPie: Pawel
 * ID: A049473
 */
package com.project.project.enums;


public enum HpQcConnectionMode {

    ON("on"),
    OFF("off");

    private final String value;

    private HpQcConnectionMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
