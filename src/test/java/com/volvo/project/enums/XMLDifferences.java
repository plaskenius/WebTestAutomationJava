package com.project.project.enums;

public enum XMLDifferences {
    NoDiffs(0), OneDiff(1);

    private XMLDifferences(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private final int value;
}
