package uk.co.rossbeazley.centralheating.core;

import uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode;

import java.util.List;

public interface Model {
    List<Option> options();

    void configure(Option option, Callback callback);

    public static interface Callback {
        void OK();

        void RANGE(HeatingTimeRange heatingTimeRange);
    }
}
