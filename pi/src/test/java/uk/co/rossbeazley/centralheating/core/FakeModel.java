package uk.co.rossbeazley.centralheating.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeModel implements Model {
    public void whenConfiguring(ConfigureAction configureAction) {
        this.configureAction = configureAction;
    }

    private ConfigureAction configureAction;
    private List<FakeOption> options;
    public FakeOption lastOptionConfigured;
    public Option lastUnknownOptionType;

    public FakeModel(List<FakeOption> options) {
        this.options = options;
        configureAction = new DefaultConfigureAction();
    }

    public FakeModel(FakeOption... options) {
        this.options = Arrays.asList(options);
        configureAction = new DefaultConfigureAction();
    }


    @Override
    public List<Option> options() {
        List<Option> result = new ArrayList<>();
        result.addAll(options);
        return result;
    }

    @Override
    public void configure(Option option, Callback callback) {
        configureAction.configure(option, callback, this);
    }


    public FakeOption lastOptionConfigured() {
        return lastOptionConfigured;
    }

    public void lastOptionConfiguredClear() {
        lastOptionConfigured = null;
    }

    public Option getLastUnknownOptionType() {
        return lastUnknownOptionType;
    }

    public static class DefaultConfigureAction implements ConfigureAction {

        public DefaultConfigureAction() {
        }

        @Override
        public void configure(Option option, Callback callback, FakeModel fakeModel) {

            fakeModel.lastUnknownOptionType = null;
            fakeModel.lastOptionConfigured = (FakeOption) option;
            if (fakeModel.lastOptionConfigured.hasSubOptions()) {
                callback.RANGE(fakeModel.lastOptionConfigured.heatingRang(), fakeModel.lastOptionConfigured.defaultValue());
                return;
            }

            callback.OK();
        }
    }
}
