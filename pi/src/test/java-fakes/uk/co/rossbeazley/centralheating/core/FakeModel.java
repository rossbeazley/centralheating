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
    public Option lastUnknownOptionType;

    public FakeModel(List<FakeOption> options) {
        this.options = options;
        configureAction = new DefaultFakeOptionConfigureAction();
    }

    public FakeModel(FakeOption... options) {
        this.options = Arrays.asList(options);
        configureAction = new DefaultFakeOptionConfigureAction();
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

    public void lastOptionConfiguredClear() {

    }

    public Option lastConfiguredOption() {
        return lastUnknownOptionType;
    }

    public static class DefaultFakeOptionConfigureAction implements ConfigureAction {

        public DefaultFakeOptionConfigureAction() {
        }

        @Override
        public void configure(Option option, Callback callback, FakeModel fakeModel) {

            fakeModel.lastUnknownOptionType = option;
            FakeOption fakeOption = (FakeOption) option;
            if (fakeOption.hasSubOptions()) {
                callback.RANGE(fakeOption.heatingRange());
            } else {
                callback.OK();
            }
        }
    }
}
