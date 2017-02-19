package uk.co.rossbeazley.centralheating.ui;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.HeatingModeOption;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingMode {

    private CapturingViewFramework capturingViewFramework;

    @Test
    public void
    showAMenuOptionForHeatingBoost() throws Exception {
        String heatingModeTitle = "Heating Mode";
        Model model = new Model(HeatingModeOption.createHeatingModeOption(heatingModeTitle));

        PresentationTier presentationTier = TestHexagonBuilder.imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems(heatingModeTitle));
    }


    @Before
    public void setUp() throws Exception {
        capturingViewFramework = new CapturingViewFramework();

    }

    @Test
    public void
    displaysConfigurationDialogForHeatingMode() throws Exception {
        Model model = new Model(HeatingModeOption.createHeatingModeOption("Heaating Moode"));
        PresentationTier presentationTier = TestHexagonBuilder.imInTheMenuview(capturingViewFramework, model);

        presentationTier.buttonPress();

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(capturingViewFramework.lastCapturedScreenClass(),is(equalTo(ConfigurationDialogView.class)));
        assertThat( capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class), isA(FakeConfigurationDialogView.class));
    }


    @Test @Ignore("To be redefined")
    public void
    presentsConfigForHeatingMode() throws Exception {

        displaysConfigurationDialogForHeatingMode();

        FakeConfigurationDialogView fakeConfigurationDialog = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(fakeConfigurationDialog.choices, hasItems("Choice1"));
    }

}
