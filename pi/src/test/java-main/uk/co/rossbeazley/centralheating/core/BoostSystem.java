package uk.co.rossbeazley.centralheating.core;

public class BoostSystem {
    private Option option;
    private GasBurner gasBurner;

    public BoostSystem(String boostTitle, GasBurner gasBurner) {
        this.gasBurner = gasBurner;
        option = new Option(boostTitle);
    }

    public Option option() {
        return option;
    }

    public void enable(Option option) {
            gasBurner.turnOn();
    }
}
