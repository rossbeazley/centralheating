package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.Option;

public class HeatingTimeRange extends Option{
    private final SelectingHeatingMode.HeatingTime from;
    private final SelectingHeatingMode.HeatingTime to;

    public HeatingTimeRange(SelectingHeatingMode.HeatingTime from, SelectingHeatingMode.HeatingTime to) {
        super("Heating Time Range");
        this.from = from;
        this.to = to;
    }

    public SelectingHeatingMode.HeatingTime getFrom() {
        return from;
    }

    public SelectingHeatingMode.HeatingTime getTo() {
        return to;
    }
}
