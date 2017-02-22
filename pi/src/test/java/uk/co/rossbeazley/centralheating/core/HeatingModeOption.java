package uk.co.rossbeazley.centralheating.core;

public class HeatingModeOption
{


    public static Option[] createHeatingModeOptions(String onName, String offName, String externalTimerName, String boostName) {
        return new Option[]{new FakeOption(onName,false),new FakeOption(offName,false),new FakeOption(externalTimerName,false),new FakeOption(boostName,true)};
    }
}
