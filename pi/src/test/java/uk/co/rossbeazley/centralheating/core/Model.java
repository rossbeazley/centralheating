package uk.co.rossbeazley.centralheating.core;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Option> options;

    public Model(String... option1) {
        options = new ArrayList<>(option1.length);
        for (String option : option1) {
            options.add(new Option(option));
        }
    }


    public List<Option> options() {
        return this.options;
    }
}
