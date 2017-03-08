package uk.co.rossbeazley.centralheating.core;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ModelTest {

    private static final Object ON = "ON";

    @Test
    public void
    haveTheOptionToTurnTheHeatingOn() throws Exception {
        Model model = new CentralHeatingSystem();
        List<Option> options = model.options();
        assertThat(options, hasItem(OptionMatcher.withTitle("on")));
    }

    @Test
    @Ignore("wip")
    public void
    turnsTheHeatingOn() throws Exception {


        Object heating = null;
        assertThat(heating, is(ON));
    }

    private static class CentralHeatingSystem implements Model {
        @Override
        public List<Option> options() {
            return singletonList(new Option("on"));
        }

        @Override
        public void configure(Option option, Callback callback) {

        }
    }
}