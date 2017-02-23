package uk.co.rossbeazley.centralheating.core;

import jdk.nashorn.internal.runtime.options.Options;
import uk.co.rossbeazley.centralheating.ui.HeatingTimeRange;
import uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode;

import java.util.List;

public interface Model {
    List<Option> options();

    void configure(Option option, Callback callback);

    public static interface Callback {
        void OK();

        void RANGE(HeatingTimeRange heatingTimeRange, SelectingHeatingMode.HeatingTime heatingTime);
    }
}
