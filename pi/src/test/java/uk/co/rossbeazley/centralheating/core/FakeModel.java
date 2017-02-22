package uk.co.rossbeazley.centralheating.core;

import java.util.Arrays;
import java.util.List;

public class FakeModel implements Model {
    private List<Option> options;
    private FakeOption lastOptionConfigured;

    public FakeModel(List<Option> options) {
        this.options = options;
    }

    public FakeModel(Option... options) {
        this.options = Arrays.asList(options);
    }


    @Override
    public List<Option> options() {
        return this.options;
    }

    @Override
    public void configure(Option option, Callback callback) {
        this.lastOptionConfigured = (FakeOption) option;
        if(lastOptionConfigured.hasSubOptions()) {
            callback.NOTOK();
        } else {
            callback.OK();
        }
    }

    public Option lastOptionConfigured() {
        return lastOptionConfigured;
    }
}
