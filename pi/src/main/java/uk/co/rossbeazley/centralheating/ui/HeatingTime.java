package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.Option;

import java.util.concurrent.TimeUnit;

public class HeatingTime extends Option {
    private long millis;

    public HeatingTime(long millis) {
        super("HeatingTime");
        this.millis = millis;
    }

    public static HeatingTime createFromTimeUnit(int value, TimeUnit unit) {
        return new HeatingTime(unit.toMillis(value));
    }

    public boolean equals(Object other) {
        return ((HeatingTime)other).millis == millis;
    }

    public String asSecondsString() {
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis));
    }

    public HeatingTime increment() {
        return new HeatingTime(millis+1000);
    }
}
