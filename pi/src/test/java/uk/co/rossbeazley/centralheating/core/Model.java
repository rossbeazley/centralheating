package uk.co.rossbeazley.centralheating.core;

import java.util.Arrays;
import java.util.List;

public class Model {
    private List<Option> options;

    public Model(List<Option> options) {
        this.options = options;
    }

    public Model(Option... options) {
        this.options = Arrays.asList(options);
    }


    public List<Option> options() {
        return this.options;
    }
}
