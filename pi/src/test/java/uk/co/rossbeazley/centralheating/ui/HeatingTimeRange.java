package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.Option;

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

    public SelectingHeatingMode.HeatingTime getFrom() {
        return from;
    }

    public SelectingHeatingMode.HeatingTime getTo() {
        return to;
    }

    //TODO think i can get rid of this
    public Option currentValue() {
        return currentOptionValue;
    }

    public SelectingHeatingMode.HeatingTime heatingTimeValue() {
        return defaultHeatingTimeValue;
    }
}
