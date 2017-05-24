package uk.co.rossbeazley.centralheating.core;

import java.util.concurrent.TimeUnit;

public class BoostSystem implements Clock.CanBeTicked {
    private Option option;
    private GasBurner gasBurner;
    private long lastTimeSeen;
    private long turnOffAt;

    public BoostSystem(String boostTitle, GasBurner gasBurner) {
        this.gasBurner = gasBurner;
        turnOffAt = Long.MAX_VALUE;
        option = new Option(boostTitle);
    }

    public Option option() {
        return option;
    }

    public void enable(Option option) {
        gasBurner.turnOn();
        this.turnOffAt = lastTimeSeen + TimeUnit.HOURS.toMillis(1);
    }

    @Override
    public void timeIsAt(long threeOClock) {
        lastTimeSeen = threeOClock;
        if (turnOffAt <= threeOClock) {
            turnOffAt = Long.MAX_VALUE;
            gasBurner.turnOff();
        }
    }

    public void disable() {
        turnOffAt = Long.MAX_VALUE;
    }
}
