package uk.co.rossbeazley.centralheating.core;

public class TemperatureOption  {

    public static FakeOption createTemperatureOption(String temperatureTitle) {
        return new FakeOption(temperatureTitle,false);
    }
}
