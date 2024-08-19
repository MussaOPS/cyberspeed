package com.scratch.game.domain;

public class Arguments {

    public String configFilePath;
    public Integer bettingAmount;

    public Arguments(String configFilePath, Integer bettingAmount) {

        this.configFilePath = configFilePath;
        this.bettingAmount = bettingAmount;
    }
}
