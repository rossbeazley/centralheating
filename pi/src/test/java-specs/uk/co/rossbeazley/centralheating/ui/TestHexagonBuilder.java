package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode.HeatingTime.createFromTimeUnit;

public class TestHexagonBuilder {

    private FakeModel model;
    private List<FakeOption> options = new ArrayList<>(10);
    private HeatingTimeRange heatingTimeRange;
    private SelectingHeatingMode.HeatingTime defaultValue;
    private String heatingBoostTitle;

    public TestHexagonBuilder withHeatingSubsystemTitled(String on, String off, String s, String boost) {
        this.heatingBoostTitle = boost;
        addOptions(HeatingModeOption.createHeatingModeOptions(on, off, s, boost));
        return this;
    }

    public TestHexagonBuilder withGenericConfigOptions(String... option1) {

        for (String option : option1) {
            addOptions(new FakeOption(option,false));
        }
        return this;
    }

    private List<FakeOption> addOptions(FakeOption... e) {
        options.addAll(Arrays.asList(e));
        return options;
    }

    public FakeModel build() {
        model = new FakeModel(options);
        return model;
    }


    public TestHexagonBuilder withAnyOptions() {
        addOptions(new FakeOption("any option",false));
        return this;
    }

    public TestHexagonBuilder withNoConfigOptions() {
        return this;
    }

    public TestHexagonBuilder withHeatingBoostSubsystemMinutesRange(String boostName, HeatingTimeRange heatingTimeRange) {
        this.heatingTimeRange = heatingTimeRange;
        this.defaultValue = defaultValue;
        this.heatingBoostTitle = boostName;
        FakeOption fakeOption = new FakeOption(boostName, true);
        fakeOption.addDefaultOption(defaultValue);
        fakeOption.addHeatingTimeRange(heatingTimeRange);
        addOptions(fakeOption);
        return this;
    }

    public TestHexagonBuilder withGenericMultiConfigOptions(String option1) {
        FakeOption fakeOption = new FakeOption(option1, true);
        fakeOption.addHeatingTimeRange(new HeatingTimeRange(createFromTimeUnit(1, SECONDS), createFromTimeUnit(3,SECONDS),null,createFromTimeUnit(2,SECONDS)));
        addOptions(fakeOption);
        return this;
    }

    public TestHexagonBuilder withHeatingSubsystemSingleOptionsTitled(String on, String off, String s) {
        addOptions(HeatingModeOption.createHeatingModeOptions(on, off, s));
        return this;
    }
}
