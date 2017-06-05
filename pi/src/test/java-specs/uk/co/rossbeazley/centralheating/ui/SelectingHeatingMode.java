package uk.co.rossbeazley.centralheating.ui;

import org.junit.Before;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.FakeModel;
import uk.co.rossbeazley.centralheating.core.FakeOption;
import uk.co.rossbeazley.centralheating.core.HeatingTimeRange;
import uk.co.rossbeazley.centralheating.core.Option;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingMode {

    private CapturingViewFramework capturingViewFramework;
    private FakeModel model;
    private String heatingModeTitle;
    private HeatingTime defaultHeatingTimeValue;

    @Before
    public void initialiseHeatingSubSystem() throws Exception {
        heatingModeTitle = "Heating Mode";
        defaultHeatingTimeValue = HeatingTime.createFromTimeUnit(2, TimeUnit.SECONDS);
        HeatingTimeRange heatingTimeRange = new HeatingTimeRange(HeatingTime.createFromTimeUnit(1, TimeUnit.SECONDS), HeatingTime.createFromTimeUnit(3, TimeUnit.SECONDS), null, defaultHeatingTimeValue);

        model = new TestHexagonBuilder()
                .withHeatingSubsystemSingleOptionsTitled("On", "Off", "External Timer Clock")
                .withHeatingBoostSubsystemMinutesRange("Boost", heatingTimeRange)
                .build();
    }


    @Test
    public void
    showMenuOptionsForHeatingMode() throws Exception {
        capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = UIContext.imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems("On","Off","External Timer Clock", "Boost"));
    }



    @Test
    public void
    selectingONDisplaysConfirmationDialog() throws Exception {
        capturingViewFramework = new CapturingViewFramework();
        CanReceiveKeyInput canReceiveKeyInput = UIContext.imInTheMenuview(capturingViewFramework, model);

        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.buttonPress();

        assertThat(model.lastConfiguredOption(), is(new FakeOption("Off",false)));
        FakeSavedDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(SavedDialogView.class);
        assertThat(fakeConfigurationDialogView, isA(FakeSavedDialogView.class));
    }

    @Test
    public void
    selectingBoostDisplaysConfigDialog() throws Exception {
        capturingViewFramework = new CapturingViewFramework();
        CanReceiveKeyInput canReceiveKeyInput = UIContext.imInTheMenuview(capturingViewFramework, model);

        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.buttonPress();

        //check we configured the option we expect
        Option fakeOption = model.lastConfiguredOption();
        Option expectedOption = new FakeOption("Boost",true)
                .addHeatingTimeRange(new HeatingTimeRange(HeatingTime.createFromTimeUnit(1, TimeUnit.SECONDS), HeatingTime.createFromTimeUnit(3, TimeUnit.SECONDS), null, defaultHeatingTimeValue));
        assertThat(fakeOption, is(expectedOption));


        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(fakeConfigurationDialogView, isA(FakeConfigurationDialogView.class));

    }


    @Test
    public void
    confirmationDialogCloses() throws Exception {
        capturingViewFramework = new CapturingViewFramework();
        CanReceiveKeyInput canReceiveKeyInput = UIContext.imInTheMenuview(capturingViewFramework, model);
        thenImInTheConfirmationDialog(canReceiveKeyInput);

        canReceiveKeyInput.buttonPress(); // <- is a close operation really

        FakeMenuView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(MenuView.class);
        assertThat(fakeConfigurationDialogView, isA(FakeMenuView.class));

    }

    public void thenImInTheConfirmationDialog(CanReceiveKeyInput canReceiveKeyInput) {
        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.buttonPress();
    }

}
