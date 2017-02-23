package uk.co.rossbeazley.centralheating.core;

import jdk.nashorn.internal.runtime.options.Options;
import uk.co.rossbeazley.centralheating.ui.HeatingTimeRange;
import uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode;

import java.util.List;
import java.util.Objects;

public class FakeOption extends Option {
    private final boolean hasSubOptions;
    private HeatingTimeRange heatingTimeRange;
    private SelectingHeatingMode.HeatingTime defaultValue;

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

    public List<Options> subOptions() {
        return null;
    }

    public void addHeatingTimeRange(HeatingTimeRange heatingTimeRange) {

        this.heatingTimeRange = heatingTimeRange;
    }

    public HeatingTimeRange heatingRang() {
        return heatingTimeRange;
    }

    public void addDefaultOption(SelectingHeatingMode.HeatingTime defaultValue) {

        this.defaultValue = defaultValue;
    }

    public SelectingHeatingMode.HeatingTime defaultValue() {
        return defaultValue;
    }
}
