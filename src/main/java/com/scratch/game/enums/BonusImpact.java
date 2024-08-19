package com.scratch.game.enums;

public enum BonusImpact {

    MULTIPLY_REWARD("multiply_reward"),
    EXTRA_BONUS("extra_bonus");

    private final String value;

    BonusImpact(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
