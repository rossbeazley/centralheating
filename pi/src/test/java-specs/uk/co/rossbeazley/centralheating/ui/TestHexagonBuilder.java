package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode.HeatingTime.createFromTimeUnit;

public class TestHexagonBuilder {

    private FakeModel model;
    private List<FakeOption> options = new ArrayList<>(10);
    private SelectingHeatingMode.HeatingTime defaultValue;

    public static FakeOption[] createHeatingModeFakeOptions(String onName, String offName, String externalTimerName) {
        return new FakeOption[]{new FakeOption(onName,false),new FakeOption(offName,false),new FakeOption(externalTimerName,false)};
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
        FakeOption fakeOption = new FakeOption(boostName, true);
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
        addOptions(createHeatingModeFakeOptions(on, off, s));
        return this;
    }
}
