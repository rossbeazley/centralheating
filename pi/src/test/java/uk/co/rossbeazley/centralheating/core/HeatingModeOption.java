package uk.co.rossbeazley.centralheating.core;

import java.util.List;

public class HeatingModeOption
{


    public static Option[] createHeatingModeOptions(String on, String off, String s, String boost) {
        return new Option[]{new Option(on,false),new Option(off,false),new Option(s,false),new Option(boost,true)};
    }
}
