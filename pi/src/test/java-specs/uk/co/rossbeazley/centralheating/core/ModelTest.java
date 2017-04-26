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

    ModelTestBuilder modelTestBuilder = new ModelTestBuilder();

    @Test
    public void
    haveTheOptionToTurnTheHeatingOn() throws Exception {

        DataFactory dataFactory = new DataFactory();
        String onOptionTitle = dataFactory.getRandomWord();

        Model model = modelTestBuilder.withOnTitle(onOptionTitle).build();
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

        Model model = modelTestBuilder.withOnTitle(onOptionTitle)
                .withOffTitle(offOptionTitle)
                .withExternalTimerTitle(externalOptionTitle)
                .build(); /* <---- need to build system with OFF */
        List<Option> options = model.options();
        assertThat(options, hasItem(OptionMatcher.withTitle(onOptionTitle)));
        assertThat(options, hasItem(OptionMatcher.withTitle(offOptionTitle)));
        assertThat(options, hasItem(OptionMatcher.withTitle(externalOptionTitle)));
    }

    @Test
    public void
    turnsTheHeatingOn() throws Exception {

        GasBurner gasBurner = new GasBurner();
        Model model = modelTestBuilder.withOnTitle("on").withGasBurner(gasBurner).build();
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
        Model model = modelTestBuilder.withGasBurner(gasBurner).build(); /* <---- need to build system with OFF */
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

        Model model = modelTestBuilder
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */

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
        Model model = modelTestBuilder
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */
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


     static class CentralHeatingSystem implements Model {

        private final Option onOption;
        private final Option offOption;

        private ExternalTimerSystem external;
        private GasBurner gasBurner;
        private List<Option> options;

        public CentralHeatingSystem(String onOptionTitle, String off, ExternalTimerSystem external, GasBurner gasBurner) {
            this.external = external;
            this.gasBurner = gasBurner;
            onOption = new Option(onOptionTitle);
            offOption = new Option(off);
            options = Arrays.asList(onOption,offOption,external.option());
        }

        @Override
        public List<Option> options() {
            return options;
        }

        @Override
        public void configure(Option option, Callback callback) {
            if(option.equals(onOption)) gasBurner.turnOn();
            else gasBurner.turnOff();
            callback.OK();
        }
    }

     static class GasBurner {

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

     static class ExternalTimer {

        public static final Object OFF = new Object(),
                                    ON = new Object();
        private ExternalTimer.Observer externalTimerObserver;

        public ExternalTimer(Object p0) {
        }

        public void addObserver(Observer observer) {
            this.externalTimerObserver = observer;
        }

        public void turnOn() {
            this.externalTimerObserver.externalTimerOn();
        }

        public interface Observer {
            void externalTimerOn();
        }
    }

     static class ExternalTimerSystem {
        private final Option option;

        public ExternalTimerSystem(String external, ExternalTimer externalTimer, GasBurner gasBurner) {
            this.option = new Option(external);
            externalTimer.addObserver(new ExternalTimer.Observer() {
                @Override
                public void externalTimerOn() {
                    gasBurner.turnOn();
                }
            });
        }

        public Option option() {
            return option;
        }
    }
}