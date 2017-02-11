package uk.co.rossbeazley.centralheating.ui;

public class TestDataBuilder {
    public static Model buildCoreModelWithConfigOptions(String option1) {
        return new Model(option1);
    }

    public static Model buildAnyCoreModel() {
        return buildCoreModelWithConfigOptions("Option1");
    }
}
