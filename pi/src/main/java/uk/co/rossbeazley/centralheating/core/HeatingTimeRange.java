package uk.co.rossbeazley.centralheating.core;

import uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode;

public class HeatingTimeRange extends Option{
    private final SelectingHeatingMode.HeatingTime from;
    private final SelectingHeatingMode.HeatingTime to;
    private Option currentOptionValue;
    private SelectingHeatingMode.HeatingTime defaultHeatingTimeValue;

    public HeatingTimeRange(SelectingHeatingMode.HeatingTime from, SelectingHeatingMode.HeatingTime to, Option currentOptionValue, SelectingHeatingMode.HeatingTime defaultHeatingTimeValue) {
        super("Heating Time Range");
        this.from = from;
        this.to = to;
        this.currentOptionValue = currentOptionValue;
        this.defaultHeatingTimeValue = defaultHeatingTimeValue;
    }

    public SelectingHeatingMode.HeatingTime heatingTimeValue() {
        return defaultHeatingTimeValue;
    }

    public void increment() {

        this.defaultHeatingTimeValue = this.defaultHeatingTimeValue.increment();
    }
}
