package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.HeatingModeOption;
import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestHexagonBuilder {

    private Model model;
    private List<Option> options = new ArrayList<>(10);

    public TestHexagonBuilder withHeatingSubsystemTitled(String on, String off, String s, String boost) {
        addOptions(HeatingModeOption.createHeatingModeOptions(on, off, s, boost));
        return this;
    }

    public TestHexagonBuilder withGenericConfigOptions(String... option1) {

        for (String option : option1) {
            addOptions(new Option(option));
        }
        return this;
    }

    private List<Option> addOptions(Option... e) {
        options.addAll(Arrays.asList(e));
        return options;
    }

    public Model build() {
        return (model = new Model(options));
    }


    public TestHexagonBuilder withAnyOptions() {
        addOptions(new Option("any option"));
        return this;
    }

    public TestHexagonBuilder withNoConfigOptions() {
        return this;
    }
}
