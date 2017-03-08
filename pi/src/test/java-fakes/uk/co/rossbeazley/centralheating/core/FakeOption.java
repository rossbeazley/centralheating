package uk.co.rossbeazley.centralheating.core;

import jdk.nashorn.internal.runtime.options.Options;
import uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode;

import java.util.List;

public class FakeOption extends Option {
    private final boolean hasSubOptions;
    private HeatingTimeRange heatingTimeRange;

    public FakeOption(String on, boolean b) {
        super(on);
        this.hasSubOptions = b;
    }

    public boolean hasSubOptions() {
        return hasSubOptions;
    }

    public boolean equals(Object other) {
        return ((FakeOption)other).hasSubOptions == hasSubOptions && ((FakeOption)other).option1.equals(option1);
    }

    public String toString() {
        return "FakeOption " + option1 + " with sub options " + hasSubOptions;
    }

    public FakeOption addHeatingTimeRange(HeatingTimeRange heatingTimeRange) {

        this.heatingTimeRange = heatingTimeRange;
        return this;
    }

    public HeatingTimeRange heatingRange() {
        return heatingTimeRange;
    }

}
