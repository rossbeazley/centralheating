package uk.co.rossbeazley.centralheating.core;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void
    haveTheOptionToTurnTheHeatingOnAndOff() throws Exception {

        DataFactory dataFactory = new DataFactory();
        String onOptionTitle = dataFactory.getRandomWord();

        String offOptionTitle = dataFactory.getRandomWord();

        Model model = buildCentralHeatingSystemWithONANDOffOption(onOptionTitle, offOptionTitle,new GasBurner());
        List<Option> options = model.options();
        assertThat(options, hasItem(OptionMatcher.withTitle(onOptionTitle)));
        assertThat(options, hasItem(OptionMatcher.withTitle(offOptionTitle)));
    }

    public CentralHeatingSystem buildCentralHeatingSystemWithONOption(String onOptionTitle, GasBurner gasBurner) {
        return new CentralHeatingSystem(onOptionTitle, null, gasBurner);
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

    @Test
    public void
    turnsTheHeatingBackOff() throws Exception {


        //Given a system thats on
        GasBurner gasBurner = new GasBurner();
        Model model = buildCentralHeatingSystemWithONANDOffOption("on", "off", gasBurner); /* <---- need to build system with OFF */
        List<Option> options = model.options();
        CollectingCallback callback = new CollectingCallback();
        model.configure(options.get(0), callback);

        //when I turn it off
        model.configure(options.get(1), callback);

        //its off
        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurner.OFF));
    }

    private Model buildCentralHeatingSystemWithONANDOffOption(String on, String off, GasBurner gasBurner) {
        return new CentralHeatingSystem(on,off, gasBurner);
    }

    private static class CentralHeatingSystem implements Model {

        private final Option onOption;
        private final Option offOption;
        private GasBurner gasBurner;

        public CentralHeatingSystem(String onOptionTitle, String off, GasBurner gasBurner) {
            this.gasBurner = gasBurner;
            onOption = new Option(onOptionTitle);
            offOption = new Option(off);
        }

        @Override
        public List<Option> options() {
            return Arrays.asList(onOption,offOption);
        }

        @Override
        public void configure(Option option, Callback callback) {
            if(option.equals(onOption)) gasBurner.turnOn();
            else gasBurner.turnOff();
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

        public void turnOff() {
            state = OFF;
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