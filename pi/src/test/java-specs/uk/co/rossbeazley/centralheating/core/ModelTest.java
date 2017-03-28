package uk.co.rossbeazley.centralheating.core;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
    haveTheOptionToTurnTheHeatingOnAndOffAndExternalTimer() throws Exception {

        DataFactory dataFactory = new DataFactory();
        String onOptionTitle = dataFactory.getRandomWord();
        String offOptionTitle = dataFactory.getRandomWord();
        String externalOptionTitle = dataFactory.getRandomWord();

        Model model = buildCentralHeatingSystemWithONANDOffOptionAndExternalTimerSupport(onOptionTitle, offOptionTitle, externalOptionTitle, new GasBurner(), null); /* <---- need to build system with OFF */
        List<Option> options = model.options();
        assertThat(options, hasItem(OptionMatcher.withTitle(onOptionTitle)));
        assertThat(options, hasItem(OptionMatcher.withTitle(offOptionTitle)));
        assertThat(options, hasItem(OptionMatcher.withTitle(externalOptionTitle)));
    }

    public CentralHeatingSystem buildCentralHeatingSystemWithONOption(String onOptionTitle, GasBurner gasBurner) {
        return new CentralHeatingSystem(onOptionTitle, null, null, gasBurner);
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
        model.configure(options.get(0), callback); //TODO make this clearer, get(0) wtf?

        //when I turn it off
        model.configure(options.get(1), callback);

        //its off
        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurner.OFF));
    }


    @Test
    public void
    enablesTheExternalTimerAndItsOff() {
        //given the external timer is off
        ExternalTimer externalTimer = new ExternalTimer(ExternalTimer.OFF);
        GasBurner gasBurner = new GasBurner();

        Model model = buildCentralHeatingSystemWithONANDOffOptionAndExternalTimerSupport("on", "off", "External", gasBurner, externalTimer); /* <---- need to build system with OFF */

        //when i set heating to external timer mode
        Option externalTimerOption = model.options().get(2);
        model.configure(externalTimerOption, new CollectingCallback());

        //then the heating is off
        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurner.OFF));
    }



    @Test
    public void
    enablesTheExternalTimerAndItTurnsOn() {

        //given i set heating to external timer mode
        //when the external timer indicates on
        //then the gas burner turns on
        GasBurner gasBurner = new GasBurner();
        ExternalTimer externalTimer = new ExternalTimer(ExternalTimer.OFF);
        Model model = buildCentralHeatingSystemWithONANDOffOptionAndExternalTimerSupport("on", "off", "External", gasBurner, externalTimer); /* <---- need to build system with OFF */
        Option externalTimerOption = model.options().get(2);
        model.configure(externalTimerOption, new CollectingCallback());

        externalTimer.turnOn();

        assertThat(gasBurner.state(), is(GasBurner.ON));
    }

    @Test @Ignore
    public void
    enablesTheExternalTimerAndItsOn() {
        fail("Spec this next +2");
    }


    @Test @Ignore
    public void
    enablesTheExternalTimerAndItTurnsOff() {
        fail("Spec this next +3");
    }



    private Model buildCentralHeatingSystemWithONANDOffOption(String on, String off, GasBurner gasBurner) {
        return new CentralHeatingSystem(on,off, "", gasBurner);
    }


    private Model buildCentralHeatingSystemWithONANDOffOptionAndExternalTimerSupport(String on, String off, String external, GasBurner gasBurner, ExternalTimer externalTimer) {
        return new CentralHeatingSystem(on,off,external, gasBurner);
    }

    private static class CentralHeatingSystem implements Model {

        private final Option onOption;
        private final Option offOption;
        private final Option externalTimerOption;
        private GasBurner gasBurner;

        public CentralHeatingSystem(String onOptionTitle, String off, String external, GasBurner gasBurner) {
            this.gasBurner = gasBurner;
            onOption = new Option(onOptionTitle);
            offOption = new Option(off);
            externalTimerOption = new Option(external);
        }

        @Override
        public List<Option> options() {
            return Arrays.asList(onOption,offOption,externalTimerOption);
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

    private static class ExternalTimer {

        public static final Object OFF = new Object(),
                                    ON = new Object();
        private ExternalTimer.Observer externalTimerObserver;

        public ExternalTimer(Object p0) {
        }

        public void addObserver(Observer observer) {
            this.externalTimerObserver = observer;
        }

        public void turnOn() {
            this.externalTimerObserver.on();
        }

        public interface Observer {
            void on();
        }
    }
}