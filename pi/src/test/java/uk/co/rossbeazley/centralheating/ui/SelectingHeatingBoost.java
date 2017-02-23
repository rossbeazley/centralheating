package uk.co.rossbeazley.centralheating.ui;

import org.junit.Before;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.FakeModel;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingBoost {


    private CapturingViewFramework capturingViewFramework;
    private FakeModel model;
    private SelectingHeatingMode.HeatingTime defaultHeatingTimeValue;
    private PresentationTier presentationTier;

    @Before
    public void initialiseHeatingSubSystem() throws Exception {
        defaultHeatingTimeValue = SelectingHeatingMode.HeatingTime.createFromTimeUnit(2, TimeUnit.SECONDS);
        HeatingTimeRange heatingTimeRange = new HeatingTimeRange(SelectingHeatingMode.HeatingTime.createFromTimeUnit(1, TimeUnit.SECONDS), SelectingHeatingMode.HeatingTime.createFromTimeUnit(3, TimeUnit.SECONDS));

        model = new TestHexagonBuilder()
                //.withHeatingSubsystemSingleOptionsTitled("On", "Off", "External Timer Clock")
                .withHeatingBoostSubsystemMinutesRange("Boost", heatingTimeRange, defaultHeatingTimeValue)
                .build();

        capturingViewFramework = new CapturingViewFramework();
        presentationTier = UIContext.imShowingTheBoostOptions(capturingViewFramework, model);

    }


    @Test
    public void
    showsBoostOptions() throws Exception { //TODO remove exception from junit method template

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);

        assertThat(fakeConfigurationDialogView,is(notNullValue()));
        String valuePresented = fakeConfigurationDialogView.choices.get(0);
        String theExpectedDefault = this.defaultHeatingTimeValue.asSecondsString();

        assertThat(valuePresented,is(theExpectedDefault));
    }


    @Test
    public void
    increaseBoostAmount() {
        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);

        presentationTier.clockWise();

        String valuePresented = fakeConfigurationDialogView.choices.get(0);
        String biggerValue="3";
        assertThat(valuePresented,is(biggerValue));
    }

}
