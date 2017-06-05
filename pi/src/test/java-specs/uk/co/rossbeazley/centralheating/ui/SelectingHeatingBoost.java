package uk.co.rossbeazley.centralheating.ui;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static uk.co.rossbeazley.centralheating.ui.HeatingTime.createFromTimeUnit;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingHeatingBoost {


    private CapturingViewFramework capturingViewFramework;
    private FakeModel model;
    private CanReceiveKeyInput canReceiveKeyInput;
    private HeatingTimeRange heatingTimeRange;

    @Before
    public void initialiseHeatingSubSystem() throws Exception {
        FakeOption expectedBoostTime = new FakeOption("expectedBoostTime",false);

        HeatingTime defaultHeatingTimeValue = createFromTimeUnit(2, SECONDS);
        heatingTimeRange = new HeatingTimeRange(createFromTimeUnit(1, SECONDS), createFromTimeUnit(3, SECONDS), expectedBoostTime, defaultHeatingTimeValue);



        model = new TestHexagonBuilder()
                //.withHeatingSubsystemSingleOptionsTitled("On", "Off", "External Timer Clock")
                .withHeatingBoostSubsystemMinutesRange("Boost", heatingTimeRange)
                .build();

        capturingViewFramework = new CapturingViewFramework();
        canReceiveKeyInput = UIContext.imShowingTheBoostOptions(capturingViewFramework, model);

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

        canReceiveKeyInput.buttonPress();
        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.clockWise();


        FakeConfigurationDialogView configurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(configurationDialogView.highlightedViewWidget(),is(FakeConfigurationDialogView.CANCEL));

        canReceiveKeyInput.buttonPress();
        assertThat(capturingViewFramework.lastCapturedScreenClass(), is(equalTo(MenuView.class)));
    }


    @Test
    public void
    increaseBoostAmount() {

        canReceiveKeyInput.clockWise();

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        String valuePresented = fakeConfigurationDialogView.choices.get(0);
        String biggerValue="3";
        assertThat(valuePresented,is(biggerValue));
    }

    @Test
    public void
    acceptingTheDefaultBoostAmount() {
        canReceiveKeyInput.buttonPress();
        canReceiveKeyInput.clockWise();

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertThat(fakeConfigurationDialogView.highlightedViewWidget(),is(FakeConfigurationDialogView.SAVE));


        model.lastOptionConfiguredClear();
        model.whenConfiguring(new ConfigureAction() {
            @Override
            public void configure(Option option, Model.Callback callback, FakeModel fakeModel) {
                fakeModel.lastUnknownOptionType = option;
                callback.OK();
            }
        });
        canReceiveKeyInput.buttonPress();

        assertThat(model.lastConfiguredOption(),is(createFromTimeUnit(2, SECONDS)));
        assertThat(capturingViewFramework.lastCapturedScreenClass(), is(equalTo(SavedDialogView.class)));

    }


    @Test
    public void
    acceptingIncreasedBoostAmount() {
        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);

        // increase boost by "one" and confirm
        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.buttonPress();

        //move to save (and check)
        canReceiveKeyInput.clockWise();
        assertThat(fakeConfigurationDialogView.highlightedViewWidget(),is(FakeConfigurationDialogView.SAVE));

        // select ok
        model.lastOptionConfiguredClear();
        model.whenConfiguring(new ConfigureAction() {
            @Override
            public void configure(Option option, Model.Callback callback, FakeModel fakeModel) {
                fakeModel.lastUnknownOptionType = option;
                callback.OK();
            }
        });
        canReceiveKeyInput.buttonPress();

        HeatingTime increasedByOne = createFromTimeUnit(3,SECONDS);
        assertThat(model.lastConfiguredOption(),is(increasedByOne));

    }




    @Test  @Ignore("WIP")
    public void
    incrementingTheBostAmountInStepsAsDefinedByTheModel() throws Exception {


    }

    @Test @Ignore("WIP")
    public void
    cantSelectValueOutwithTheRange() throws Exception {


    }
}
