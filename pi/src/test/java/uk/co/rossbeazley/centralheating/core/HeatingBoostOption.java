package uk.co.rossbeazley.centralheating.core;

public class HeatingBoostOption  {


    public static FakeOption createHeatingBoostOption(String option1) {
        return new FakeOption(option1,false);
    }
}
