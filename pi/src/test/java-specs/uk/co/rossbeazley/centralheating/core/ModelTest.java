package uk.co.rossbeazley.centralheating.core;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ModelTest {

    @Test
    public void
    haveTheOptionToTurnTheHeatingOn() throws Exception {

        DataFactory dataFactory = new DataFactory();
        String onOptionTitle = dataFactory.getRandomWord();

        Model model = buildCentralHeatingSystemWithONOption(onOptionTitle, null);
        List<Option> options = model.options();
        assertThat(options, hasItem(OptionMatcher.withTitle(onOptionTitle)));
    }

    public CentralHeatingSystem buildCentralHeatingSystemWithONOption(String onOptionTitle, GasBurner gasBurner) {
        return new CentralHeatingSystem(onOptionTitle, gasBurner);
    }

    @Test
    public void
    turnsTheHeatingOn() throws Exception {

        GasBurner gasBurner = new GasBurner();
        Model model = buildCentralHeatingSystemWithONOption("on", gasBurner);
        List<Option> options = model.options();
        CollectingCallback callback = new CollectingCallback();
        model.configure(options.get(0), callback);
        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurner.ON));
        assertThat(callback.ok, is(CollectingCallback.SET));
    }

    private static class CentralHeatingSystem implements Model {

        private final Option onOption;
        private GasBurner gasBurner;

        public CentralHeatingSystem(String onOptionTitle, GasBurner gasBurner) {
            this.gasBurner = gasBurner;
            onOption = new Option(onOptionTitle);
        }

        @Override
        public List<Option> options() {
            return singletonList(onOption);
        }

        @Override
        public void configure(Option option, Callback callback) {
            gasBurner.turnOn();
            callback.OK();
        }
    }

    private static class GasBurner {

        public static final Object OFF = "GAS OFF",
                ON = "GAS ON";

        private static Object state = OFF;

        public Object state() {
            return state;
        }

        public void turnOn() {
            state = ON;
        }
    }

    private static class CollectingCallback implements Model.Callback {

        public static final Object SET = "set",
                UNSET = "unset";

        public Object ok = UNSET;

        @Override
        public void OK() {
            ok = SET;
        }

        @Override
        public void RANGE(HeatingTimeRange heatingTimeRange) {

        }
    }
}