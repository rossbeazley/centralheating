package uk.co.rossbeazley.centralheating.core;

public class TemperatureOption  {

    public static Option createTemperatureOption(String temperatureTitle) {
        return new Option(temperatureTitle);
    }
}
