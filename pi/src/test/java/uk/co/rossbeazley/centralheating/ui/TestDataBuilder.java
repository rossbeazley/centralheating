package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.Option;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuilder {
    public static Model buildCoreModelWithConfigOptions(String... option1) {
        List<Option> options = new ArrayList<>(option1.length);
        for (String option : option1) {
            options.add(new Option(option));
        }
        return new Model(options);
    }

    public static Model buildAnyCoreModel() {
        return buildCoreModelWithConfigOptions("Option1");
    }
}
