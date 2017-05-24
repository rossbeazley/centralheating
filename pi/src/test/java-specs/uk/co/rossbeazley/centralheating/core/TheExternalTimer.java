package uk.co.rossbeazley.centralheating.core;

import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TheExternalTimer {

    @Test
    public void
    gasIsOffWhenTheSystemStarts() {
        ModelTestBuilder modelTestBuilder = new ModelTestBuilder();
        modelTestBuilder.build();

        GasBurnerFake gasBurner = modelTestBuilder.adapters().gasBurner;
        assertThat(gasBurner.state(),is(GasBurnerFake.OFF));
    }

    @Test
    public void
    haveTheOptionToTurnTheHeatingOn() throws Exception {

        DataFactory dataFactory = new DataFactory();
        String onOptionTitle = dataFactory.getRandomWord();

        Model model = new ModelTestBuilder().withOnTitle(onOptionTitle).build();
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

        Model model = new ModelTestBuilder().withOnTitle(onOptionTitle)
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

        GasBurnerFake gasBurner = new GasBurnerFake();
        Model model = new ModelTestBuilder().withOnTitle("on").withGasBurner(gasBurner).build();
        List<Option> options = model.options();
        CollectingCallback callback = new CollectingCallback();
        model.configure(options.get(0), callback);
        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurnerFake.ON));
        assertThat(callback.ok, is(CollectingCallback.SET));
    }

    @Test
    public void
    turnsTheHeatingBackOff() throws Exception {


        //Given a system thats on
        GasBurnerFake gasBurner = new GasBurnerFake();
        Model model = new ModelTestBuilder().withGasBurner(gasBurner).build(); /* <---- need to build system with OFF */
        List<Option> options = model.options();
        CollectingCallback callback = new CollectingCallback();
        model.configure(options.get(0), callback); //TODO make this clearer, get(0) wtf?

        //when I turn it off
        model.configure(options.get(1), callback);

        //its off
        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurnerFake.OFF));
    }


    @Test
    public void
    enablesTheExternalTimerAndItsOff() {
        //given the external timer is off
        ExternalTimer externalTimer = new ExternalTimerFake(ExternalTimerFake.OFF);
        GasBurnerFake gasBurner = new GasBurnerFake();
        assertThat("Precondition failed, gasburner", gasBurner.state(), is(GasBurnerFake.OFF));

        String externalTimerTitle = "EXTERNAL";
        Model model = new ModelTestBuilder()
                .withExternalTimerTitle(externalTimerTitle)
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */

        //when i set heating to external timer mode
        Option externalTimerOption = model.options().get(2);
        assertThat(externalTimerOption.name(), is(externalTimerTitle));

        model.configure(externalTimerOption, new CollectingCallback());

        //then the heating is off
        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurnerFake.OFF));
    }


    @Test
    public void
    enablesTheExternalTimerAndItTurnsOn() {

        //given i set heating to external timer mode
        //when the external timer indicates on
        //then the gas burner turns on
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimerFake externalTimer = new ExternalTimerFake(ExternalTimerFake.OFF);
        Model model = new ModelTestBuilder()
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */
        Option externalTimerOption = model.options().get(2);
        model.configure(externalTimerOption, new CollectingCallback());

        externalTimer.turnOn();

        assertThat(gasBurner.state(), is(GasBurnerFake.ON));
    }

    @Test
    public void
    enablesTheExternalTimerAndItsOn() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimer externalTimer = new ExternalTimerFake(ExternalTimerFake.ON);

        Model model = new ModelTestBuilder()
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .withExternalTimerTitle("External")
                .build(); /* <---- need to build system with OFF */
        Option externalTimerOption = model.options().get(2);
        assertThat("precondition", externalTimerOption.name(),is("External"));
        model.configure(externalTimerOption, new CollectingCallback());


        assertThat(gasBurner.state(), is(GasBurnerFake.ON));
    }


    @Test
    public void
    enablesTheExternalTimerAndItTurnsOff() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimerFake externalTimer = new ExternalTimerFake(ExternalTimerFake.ON);

        Model model = new ModelTestBuilder()
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */
        Option externalTimerOption = model.options().get(2);
        model.configure(externalTimerOption, new CollectingCallback());

        externalTimer.turnOff();

        assertThat(gasBurner.state(), is(GasBurnerFake.OFF));
    }


    @Test
    public void
    externalTimerIsOnButOptionNotEnabled() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimer externalTimer = new ExternalTimerFake(ExternalTimerFake.ON);

        Model model = new ModelTestBuilder()
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */


        assertThat(gasBurner.state(), is(GasBurnerFake.OFF));
    }

    @Test
    public void
    externalTimerIsOnButOffOptionEnabled() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimer externalTimer = new ExternalTimerFake(ExternalTimerFake.ON);

        Model model = new ModelTestBuilder()
                .withOffTitle("OFF")
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */


        List<Option> options = model.options();


        Option offOption = options.get(1);

        assertThat("Precondition", offOption.name(), is("OFF"));

        model.configure(offOption, new CollectingCallback());

        assertThat(gasBurner.state(), is(GasBurnerFake.OFF));
    }


    @Test
    public void
    externalTimerTurnsOnButOptionDisabled() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimer externalTimer = new ExternalTimerFake(ExternalTimerFake.OFF);

        Model model = new ModelTestBuilder()
                .withOffTitle("OFF")
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build(); /* <---- need to build system with OFF */


        List<Option> options = model.options();


        Option offOption = options.get(1);

        assertThat("Precondition", offOption.name(), is("OFF"));

        model.configure(offOption, new CollectingCallback());

        assertThat(gasBurner.state(), is(GasBurnerFake.OFF));
    }


    @Test
    public void
    externalTimerTurnsOnAfterOptionIsDisabledInFavourOfOff() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimerFake externalTimer = new ExternalTimerFake(ExternalTimerFake.OFF);

        Model model = new ModelTestBuilder()
                .withOffTitle("OFF")
                .withExternalTimerTitle("External")
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build();


        List<Option> options = model.options();


        Option externalTimerOption = model.options().get(2);
        assertThat("Precondition", externalTimerOption.name(), is("External"));
        model.configure(externalTimerOption, new CollectingCallback());

        Option offOption = options.get(1);

        assertThat("Precondition", offOption.name(), is("OFF"));
        model.configure(offOption, new CollectingCallback());

        externalTimer.turnOn();

        assertThat(gasBurner.state(), is(GasBurnerFake.OFF));
    }



    @Test
    public void
    externalTimerTurnsOnAfterOptionIsDisabledInFavourOfOn() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimerFake externalTimer = new ExternalTimerFake(ExternalTimerFake.OFF);

        Model model = new ModelTestBuilder()
                .withOnTitle("ON")
                .withExternalTimerTitle("External")
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build();


        List<Option> options = model.options();


        Option externalTimerOption = model.options().get(2);
        assertThat("Precondition", externalTimerOption.name(), is("External"));
        model.configure(externalTimerOption, new CollectingCallback());

        Option offOption = options.get(0);

        assertThat("Precondition", offOption.name(), is("ON"));
        model.configure(offOption, new CollectingCallback());

        externalTimer.turnOn();

        assertThat(gasBurner.state(), is(GasBurnerFake.ON));
    }

    @Test
    public void
    externalTimerTurnsOffAfterOptionIsDisabledInFavourOfOn() {
        GasBurnerFake gasBurner = new GasBurnerFake();
        ExternalTimerFake externalTimer = new ExternalTimerFake(ExternalTimerFake.ON);

        Model model = new ModelTestBuilder()
                .withOnTitle("ON")
                .withExternalTimerTitle("External")
                .withGasBurner(gasBurner)
                .withExternalTimer(externalTimer)
                .build();


        List<Option> options = model.options();


        Option externalTimerOption = model.options().get(2);
        assertThat("Precondition", externalTimerOption.name(), is("External"));
        model.configure(externalTimerOption, new CollectingCallback());

        Option offOption = options.get(0);

        assertThat("Precondition", offOption.name(), is("ON"));
        model.configure(offOption, new CollectingCallback());

        externalTimer.turnOff();

        assertThat(gasBurner.state(), is(GasBurnerFake.ON));
    }


}