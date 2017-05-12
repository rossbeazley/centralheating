package uk.co.rossbeazley.centralheating.core;

import java.util.Arrays;
import java.util.List;

class CentralHeatingSystem implements Model {

    private final Option onOption;
    private final Option offOption;

    private ExternalTimerSystem external;
    private GasBurner gasBurner;
    private List<Option> options;

    public CentralHeatingSystem(String onOptionTitle, String off, ExternalTimerSystem external, GasBurner gasBurner) {
        this.external = external;
        this.gasBurner = gasBurner;
        onOption = new Option(onOptionTitle);
        offOption = new Option(off);
        options = Arrays.asList(onOption, offOption, external.option());
    }

    @Override
    public List<Option> options() {
        return options;
    }

    @Override
    public void configure(Option option, Callback callback) {
        external.disable();
        if (option.equals(onOption)) {
            gasBurner.turnOn();
        } else if (option.equals(external.option())) {
            external.enable(option);
        } else {
            gasBurner.turnOff();
        }
        callback.OK();
    }
}
