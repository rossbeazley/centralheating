package uk.co.rossbeazley.centralheating.core;

public class Option {
    private final boolean hasSubOptions;
    private String option1;

    public Option(String on, boolean b) {
        option1 = on;
        this.hasSubOptions = b;
    }

    public String name() {
        return option1;
    }

    public boolean hasSubOptions() {
        return hasSubOptions;
    }
}
