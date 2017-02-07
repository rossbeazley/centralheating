package uk.co.rossbeazley.centralheating.ui;

import java.util.Collections;
import java.util.List;

class Model {
    private List<Option> options;

    public Model(String option1) {
        options = Collections.singletonList(new Option(option1));
    }

    public static class Option {
        private String option1;

        public Option(String option1) {
            this.option1 = option1;
        }

        public String name() {
            return option1;
        }
    }

    public List<Option> options() {
        return this.options;
    }
}
