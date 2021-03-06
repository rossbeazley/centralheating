package uk.co.rossbeazley.centralheating.core;

import org.junit.Test;

import java.util.List;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class Boost {

    @Test
    public void
    hasABoostOption() throws Exception {
        Model model = new ModelTestBuilder().withBoostTitle("BOOST").build();
        List<Option> options = model.options();
        Option option = options.get(3);

        assertThat(option.name(), is("BOOST"));
    }

    @Test
    public void
    selectingBoostTurnsTheGasOn() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        Model model = new ModelTestBuilder().withBoostTitle("BOOST").withGasBurner(gasBurner).build();
        CollectingCallback callback = enableBoost(model);

        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurnerFake.ON));
        assertThat(callback.ok, is(CollectingCallback.SET));
    }

    public CollectingCallback enableBoost(Model model) {
        List<Option> options = model.options();
        CollectingCallback callback = new CollectingCallback();
        Option option = options.get(3);
        model.configure(option, callback);
        return callback;
    }


    @Test
    public void
    theOneWhereTimeElapsesAndTheHeatingTurnsOff() throws Exception {
        ClockFake clock = new ClockFake();
        GasBurnerFake gasBurner = new GasBurnerFake();
        Model model = new ModelTestBuilder().withBoostTitle("BOOST").withGasBurner(gasBurner).withClock(clock).build();

        long threeOClock = HOURS.toMillis(3);
        clock.timeIsAt(threeOClock);

        enableBoost(model);

        long fourOClock = HOURS.toMillis(4);
        clock.timeIsAt(fourOClock);

        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurnerFake.OFF));

    }


    @Test
    public void
    theOneWhereTimeElapsesAfterForcingHeatingToOn() throws Exception {

        ClockFake clock = new ClockFake();
        GasBurnerFake gasBurner = new GasBurnerFake();
        Model model = new ModelTestBuilder().withOnTitle("ON")
                .withBoostTitle("BOOST")
                .withGasBurner(gasBurner)
                .withClock(clock).build();

        long threeOClock = HOURS.toMillis(3);
        clock.timeIsAt(threeOClock);

        enableBoost(model);

        justTurnOn(model);


        long fourOClock = HOURS.toMillis(4);
        clock.timeIsAt(fourOClock);

        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurnerFake.ON));
    }

    @Test
    public void
    weAreBoostingAndSwitchToExternalTimerThatsOn() throws Exception {
        ClockFake clock = new ClockFake();
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimer externalTimer = new ExternalTimerFake(ExternalTimerFake.ON);

        Model model = new ModelTestBuilder()
                .withOnTitle("ON")
                .withBoostTitle("BOOST")
                .withGasBurner(gasBurner)
                .withClock(clock)
                .withExternalTimerTitle("EXTERNAL")
                .withExternalTimer(externalTimer)
                .build();

        long threeOClock = HOURS.toMillis(3);
        clock.timeIsAt(threeOClock);

        enableBoost(model);

        enableExternalModeThatsOn(model);


        long fourOClock = HOURS.toMillis(4);
        clock.timeIsAt(fourOClock);

        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurnerFake.ON));
    }

    private void enableExternalModeThatsOn(Model model) {
        List<Option> options = model.options();
        Option option = options.get(2);
        assertThat(option, OptionMatcher.withTitle("EXTERNAL"));
        model.configure(option, new CollectingCallback());
    }

    public void justTurnOn(Model model) {
        List<Option> options = model.options();
        Option option = options.get(0);
        assertThat(option, OptionMatcher.withTitle("ON"));
        model.configure(option, new CollectingCallback());
    }


}
