package uk.co.rossbeazley.centralheating.ui;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.FakeModel;
import uk.co.rossbeazley.centralheating.core.FakeOption;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static uk.co.rossbeazley.centralheating.ui.SelectingHeatingMode.HeatingTime.createFromTimeUnit;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingBoost {


    private CapturingViewFramework capturingViewFramework;
    private FakeModel model;
    private PresentationTier presentationTier;
    private HeatingTimeRange heatingTimeRange;

    @Before
    public void initialiseHeatingSubSystem() throws Exception {
        FakeOption expectedBoostTime = new FakeOption("expectedBoostTime",false);

        SelectingHeatingMode.HeatingTime defaultHeatingTimeValue = createFromTimeUnit(2, SECONDS);
        heatingTimeRange = new HeatingTimeRange(createFromTimeUnit(1, SECONDS), createFromTimeUnit(3, SECONDS), expectedBoostTime, defaultHeatingTimeValue);



        model = new TestHexagonBuilder()
                //.withHeatingSubsystemSingleOptionsTitled("On", "Off", "External Timer Clock")
                .withHeatingBoostSubsystemMinutesRange("Boost", heatingTimeRange)
                .build();

        capturingViewFramework = new CapturingViewFramework();
        presentationTier = UIContext.imShowingTheBoostOptions(capturingViewFramework, model);

    }


    @Test
    public void
    showsSelectedBoostOptions() throws Exception { //TODO remove exception from junit method template

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);

        assertThat(fakeConfigurationDialogView,is(notNullValue()));
        String valuePresented = fakeConfigurationDialogView.choices.get(0);
        String theExpectedDefault = this.heatingTimeRange.heatingTimeValue().asSecondsString();

        assertThat(fakeConfigurationDialogView.highlightedViewWidget(),is(FakeConfigurationDialogView.CHOICES));
        assertThat(valuePresented,is(theExpectedDefault));

    }



    @Test
    public void
    cancellingTheBoostAmount() {

        presentationTier.buttonPress();
        presentationTier.clockWise();
        presentationTier.clockWise();


        FakeConfigurationDialogView configurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(configurationDialogView.highlightedViewWidget(),is(FakeConfigurationDialogView.CANCEL));

        presentationTier.buttonPress();
        assertThat(capturingViewFramework.lastCapturedScreenClass(), is(equalTo(MenuView.class)));
    }


    @Test
    public void
    increaseBoostAmount() {

        presentationTier.clockWise();

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        String valuePresented = fakeConfigurationDialogView.choices.get(0);
        String biggerValue="3";
        assertThat(valuePresented,is(biggerValue));
    }

    @Test
    public void
    acceptingTheBoostAmount() {
        presentationTier.buttonPress();
        presentationTier.clockWise();

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(fakeConfigurationDialogView.highlightedViewWidget(),is(FakeConfigurationDialogView.SAVE));


        model.lastOptionConfiguredClear();

        presentationTier.buttonPress();

        assertThat(model.lastOptionConfigured(), is(not(nullValue())));
        FakeConfigurationDialogView configurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(capturingViewFramework.lastCapturedScreenClass(), is(equalTo(ConfirmationDialogView.class)));

    }


    @Test  @Ignore("WIP")
    public void
    incrementingTheBostAmountInSteps() throws Exception {


    }
}
