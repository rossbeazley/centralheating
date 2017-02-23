package uk.co.rossbeazley.centralheating.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeModel implements Model {
    private List<FakeOption> options;
    private FakeOption lastOptionConfigured;

    public FakeModel(List<FakeOption> options) {
        this.options = options;
    }

    public FakeModel(FakeOption... options) {
        this.options = Arrays.asList(options);
    }


    @Override
    public List<Option> options() {
        List<Option> result = new ArrayList<>();
        result.addAll(options);
        return result;
    }

    @Override
    public void configure(Option option, Callback callback) {
        this.lastOptionConfigured = (FakeOption) option;
        if(lastOptionConfigured.hasSubOptions()) {
            callback.RANGE(lastOptionConfigured.heatingRang(),lastOptionConfigured.defaultValue());
        } else {
            callback.OK();
        }
    }

    public FakeOption lastOptionConfigured() {
        return lastOptionConfigured;
    }

    public void lastOptionConfiguredClear() {
        lastOptionConfigured = null;
    }
}
