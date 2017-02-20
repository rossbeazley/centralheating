package uk.co.rossbeazley.centralheating.core;

import java.util.List;

public class HeatingModeOption
{

    public static Option createHeatingModeOption(String heatingModeTitle) {
        return new Option(heatingModeTitle);
    }

    public static Option[] createHeatingModeOptions(String on, String off, String s, String boost) {
        return new Option[]{new Option(on),new Option(off),new Option(s),new Option(boost)};
    }
}
