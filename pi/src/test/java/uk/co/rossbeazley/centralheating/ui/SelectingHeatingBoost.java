package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.FakeModel;
import uk.co.rossbeazley.centralheating.core.HeatingBoostOption;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingBoost {

    @Test
    public void
    showAMenuOptionForHeatingBoost() throws Exception {

        String heatingBoostTitle = "Heaating Boost";
        Model model = new FakeModel(HeatingBoostOption.createHeatingBoostOption(heatingBoostTitle));
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems(heatingBoostTitle));
    }


    @Test
    public void
    displaysConfigurationDialog() throws Exception {


        Model model = new FakeModel(HeatingBoostOption.createHeatingBoostOption("Heaating Boost"));
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, model);

        presentationTier.buttonPress();

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();

        assertThat(capturingViewFramework.lastCapturedScreenClass(),is(equalTo(ConfigurationDialogView.class)));
    }



    private static PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework, model);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return presentationTier;
    }

}
