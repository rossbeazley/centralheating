package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestHexagonBuilder {

    private FakeModel model;
    private List<Option> options = new ArrayList<>(10);

    public TestHexagonBuilder withHeatingSubsystemTitled(String on, String off, String s, String boost) {
        addOptions(HeatingModeOption.createHeatingModeOptions(on, off, s, boost));
        return this;
    }

    public TestHexagonBuilder withGenericConfigOptions(String... option1) {

        for (String option : option1) {
            addOptions(new FakeOption(option,false));
        }
        return this;
    }

    private List<Option> addOptions(Option... e) {
        options.addAll(Arrays.asList(e));
        return options;
    }

    public FakeModel build() {
        return (model = new FakeModel(options));
    }


    public TestHexagonBuilder withAnyOptions() {
        addOptions(new FakeOption("any option",false));
        return this;
    }

    public TestHexagonBuilder withNoConfigOptions() {
        return this;
    }

    public TestHexagonBuilder withHeatingBoostSubsystemMinutesRange(int from, int to) {
        return this;
    }
}
