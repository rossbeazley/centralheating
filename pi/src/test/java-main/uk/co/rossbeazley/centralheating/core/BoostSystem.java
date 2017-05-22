package uk.co.rossbeazley.centralheating.core;

public class BoostSystem {
    private Option option;

    public BoostSystem(String boostTitle) {
        option = new Option(boostTitle);
    }

    public Option option() {
        return option;
    }

}
