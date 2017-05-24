package uk.co.rossbeazley.centralheating.core;

import uk.co.rossbeazley.centralheating.ui.HeatingTime;

public class HeatingTimeRange extends Option{
    private final HeatingTime from;
    private final HeatingTime to;
    private Option currentOptionValue;
    private HeatingTime defaultHeatingTimeValue;

    public HeatingTimeRange(HeatingTime from, HeatingTime to, Option currentOptionValue, HeatingTime defaultHeatingTimeValue) {
        super("Heating Time Range");
        this.from = from;
        this.to = to;
        this.currentOptionValue = currentOptionValue;
        this.defaultHeatingTimeValue = defaultHeatingTimeValue;
    }

    public HeatingTime heatingTimeValue() {
        return defaultHeatingTimeValue;
    }

    public void increment() {

        this.defaultHeatingTimeValue = this.defaultHeatingTimeValue.increment();
    }
}
