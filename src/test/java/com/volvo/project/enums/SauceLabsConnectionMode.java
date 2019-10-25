/*
 * author PawelPie: Pawel
 * ID: A049473
 */
package com.project.project.enums;


public enum SauceLabsConnectionMode {

    ON("ON"),
    OFF("OFF");

    private final String value;

    private SauceLabsConnectionMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
