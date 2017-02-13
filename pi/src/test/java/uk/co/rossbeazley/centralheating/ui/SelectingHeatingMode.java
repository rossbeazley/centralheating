package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.HeatingModeOption;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingMode {

    @Test
    public void
    showAMenuOptionForHeatingBoost() throws Exception {

        String heatingModeTitle = "Heating Mode";
        Model model = new Model(new HeatingModeOption(heatingModeTitle));
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = TestDataBuilder.imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems(heatingModeTitle));
    }


    @Test
    public void
    displaysConfigurationDialog() throws Exception {


        Model model = new Model(new HeatingModeOption("Heaating Moode"));
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = TestDataBuilder.imInTheMenuview(capturingViewFramework, model);

        presentationTier.buttonPress();

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();

        assertThat(capturingViewFramework.lastCapturedScreenClass(),is(equalTo(ConfigurationDialogView.class)));
    }


}
