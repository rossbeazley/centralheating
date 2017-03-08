package uk.co.rossbeazley.centralheating.core;

public class Option {
    protected String option1;

    public Option(String on) {
        option1 = on;
    }

    public String name() {
        return option1;
    }
}
