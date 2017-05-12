package uk.co.rossbeazley.centralheating.core;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Boost {

    @Test
    public void
    hasABoostOption() throws Exception {
        Model model = new ModelTestBuilder().withBoostTitle("BOOST").build();
        List<Option> options = model.options();
        CollectingCallback callback = new CollectingCallback();
        Option option = options.get(3);

        assertThat(option.name(),is("BOOST"));


    }

    @Test
    public void
    selectingBoostTurnsTheGasOn() {
        GasBurner gasBurner = new GasBurner();
        Model model = new ModelTestBuilder().withBoostTitle("BOOST").withGasBurner(gasBurner).build();
        List<Option> options = model.options();
        CollectingCallback callback = new CollectingCallback();
        Option option = options.get(3);
        model.configure(option, callback);

        Object heating = gasBurner.state();
        assertThat(heating, is(GasBurner.ON));
        assertThat(callback.ok, is(CollectingCallback.SET));
    }
}
