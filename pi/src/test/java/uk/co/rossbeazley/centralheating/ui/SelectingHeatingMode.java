package uk.co.rossbeazley.centralheating.ui;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingMode {

    private CapturingViewFramework capturingViewFramework;
    private Model model;
    private String heatingModeTitle;

    @Before
    public void initialiseHeatingSubSystem() throws Exception {
        heatingModeTitle = "Heating Mode";
        model = new TestHexagonBuilder().withHeatingSubsystemTitled("On","Off","External Timer Clock", "Boost").build();
    }


    @Test
    public void
    showMenuOptionsForHeatingMode() throws Exception {
        capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = UIContext.imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems("On","Off","External Timer Clock", "Boost"));
    }

    @Test
    public void
    selectingBoostDisplaysConfigDialog() throws Exception {
        capturingViewFramework = new CapturingViewFramework();
        PresentationTier presentationTier = UIContext.imInTheMenuview(capturingViewFramework, model);

        presentationTier.clockWise();
        presentationTier.clockWise();
        presentationTier.clockWise();
        presentationTier.buttonPress();

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(fakeConfigurationDialogView, isA(FakeConfigurationDialogView.class));
    }

}
