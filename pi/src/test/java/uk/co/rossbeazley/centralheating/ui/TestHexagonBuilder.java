package uk.co.rossbeazley.centralheating.ui;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import uk.co.rossbeazley.centralheating.core.HeatingModeOption;
import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.Option;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestHexagonBuilder {

    private Model model;
    private List<Option> options = new ArrayList<>(10);

    public TestHexagonBuilder withHeatingSubsystemTitled(String heatingModeTitle) {
        addOption(HeatingModeOption.createHeatingModeOption(heatingModeTitle));
        return this;
    }

    public TestHexagonBuilder withGenericConfigOptions(String... option1) {

        for (String option : option1) {
            addOption(new Option(option));
        }
        return this;
    }

    private List<Option> addOption(Option e) {
        options.add(e);
        return options;
    }

    public Model build() {
        return (model = new Model(options));
    }


    public TestHexagonBuilder withAnyOptions() {
        addOption(new Option("any option"));
        return this;
    }

    public TestHexagonBuilder withNoConfigOptions() {
        return this;
    }
}
