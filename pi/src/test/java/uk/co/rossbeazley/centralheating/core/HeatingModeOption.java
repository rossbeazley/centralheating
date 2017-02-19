package uk.co.rossbeazley.centralheating.core;

import java.util.List;

public class HeatingModeOption
{

    public static Option createHeatingModeOption(String heatingModeTitle) {
        return new Option(heatingModeTitle);
    }
}
