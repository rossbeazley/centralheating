package uk.co.rossbeazley.centralheating.core;

public class HeatingModeOption
{


    public static FakeOption[] createHeatingModeOptions(String onName, String offName, String externalTimerName) {
        return new FakeOption[]{new FakeOption(onName,false),new FakeOption(offName,false),new FakeOption(externalTimerName,false)};
    }

    public static FakeOption[] createHeatingModeOptions(String onName, String offName, String externalTimerName, String boostName) {
        return new FakeOption[]{new FakeOption(onName,false),new FakeOption(offName,false),new FakeOption(externalTimerName,false),new FakeOption(boostName,true)};
    }
}
