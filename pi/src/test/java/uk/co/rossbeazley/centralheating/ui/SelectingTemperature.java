package uk.co.rossbeazley.centralheating.ui;

import org.junit.Ignore;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.HeatingModeOption;
import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.TemperatureOption;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingTemperature {

    @Test
    public void
    showAMenuOptionForHeatingBoost() throws Exception {

        String temperatureTitle = "TemperatureOption Mode";
        Model model = new Model(new TemperatureOption(temperatureTitle));
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems(temperatureTitle));
    }


    @Test @Ignore("WIP")
    public void
    displaysConfigurationDialog() throws Exception {


        Model model = new Model(new TemperatureOption("TEMP Moode"));
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, model);

        presentationTier.buttonPress();

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();

        assertThat(capturingViewFramework.lastCapturedScreenClass(),is(equalTo(RangeDialogView.class)));
    }



    private static PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework, model);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return presentationTier;
    }

}
