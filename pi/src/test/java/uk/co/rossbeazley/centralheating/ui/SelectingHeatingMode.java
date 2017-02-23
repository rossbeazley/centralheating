package uk.co.rossbeazley.centralheating.ui;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.FakeModel;
import uk.co.rossbeazley.centralheating.core.FakeOption;

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
        HeatingTimeRange heatingTimeRange = new HeatingTimeRange(HeatingTime.createFromTimeUnit(1, TimeUnit.SECONDS), HeatingTime.createFromTimeUnit(3, TimeUnit.SECONDS));

        model = new TestHexagonBuilder()
                .withHeatingSubsystemSingleOptionsTitled("On", "Off", "External Timer Clock")
                .withHeatingBoostSubsystemMinutesRange("Boost", heatingTimeRange, defaultHeatingTimeValue)
                .build();
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
    selectingONDisplaysConfirmationDialog() throws Exception {
        capturingViewFramework = new CapturingViewFramework();
        PresentationTier presentationTier = UIContext.imInTheMenuview(capturingViewFramework, model);

        presentationTier.clockWise();
        presentationTier.buttonPress();

        assertThat(model.lastOptionConfigured(), is(new FakeOption("Off",false)));
        FakeConfirmationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfirmationDialogView.class);
        assertThat(fakeConfigurationDialogView, isA(FakeConfirmationDialogView.class));
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
        assertThat(model.lastOptionConfigured().name(),is("Boost"));
        assertThat(model.lastOptionConfigured().defaultValue(),is(notNullValue()));
    }


    @Test
    public void
    showsBoostOptions() throws Exception {

        capturingViewFramework = new CapturingViewFramework();
        PresentationTier presentationTier = UIContext.imInTheMenuview(capturingViewFramework, model);
        imShowingTheBoostOptions(presentationTier);

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);

        assertThat(fakeConfigurationDialogView,is(notNullValue()));
        String valuePresented = fakeConfigurationDialogView.choices.get(0);
        String theExpectedDefault = this.defaultHeatingTimeValue.asSecondsString();

        assertThat(valuePresented,is(theExpectedDefault));
    }

    public void imShowingTheBoostOptions(PresentationTier presentationTier) {
        presentationTier.clockWise();
        presentationTier.clockWise();
        presentationTier.clockWise();
        presentationTier.buttonPress();
    }


    @Test @Ignore("Dont do yet")
    public void
    confirmationDialogCloses() throws Exception {
        capturingViewFramework = new CapturingViewFramework();
        PresentationTier presentationTier = UIContext.imInTheMenuview(capturingViewFramework, model);
        thenImInTheConfirmationDialog(presentationTier);

        presentationTier.buttonPress(); // <- is a close operation really

        FakeMenuView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(MenuView.class);
        assertThat(fakeConfigurationDialogView, isA(FakeMenuView.class));

    }

    public void thenImInTheConfirmationDialog(PresentationTier presentationTier) {
        presentationTier.clockWise();
        presentationTier.buttonPress();
    }

    public static class HeatingTime {
        private long millis;

        public HeatingTime(long millis) {

            this.millis = millis;
        }

        public static HeatingTime createFromTimeUnit(int value, TimeUnit unit) {
            return new HeatingTime(unit.toMillis(value));
        }

        public boolean equals(Object other) {
            return ((HeatingTime)other).millis == millis;
        }

        public String asSecondsString() {
            return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis));
        }
    }
}
