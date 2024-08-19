package com.scratch.game.enums;

public enum WinCombination {

    SAME_SYMBOLS("same_symbols"),
    LINEAR_SYMBOLS("linear_symbols");

    private final String value;

    WinCombination(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
