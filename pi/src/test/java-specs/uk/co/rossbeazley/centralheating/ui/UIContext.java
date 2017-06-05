package uk.co.rossbeazley.centralheating.ui;

import org.hamcrest.Matcher;
import uk.co.rossbeazley.centralheating.core.*;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UIContext {
        static CanReceiveKeyInput imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
            CanReceiveKeyInput canReceiveKeyInput = new PresentationTier(capturingViewFramework, model);
            canReceiveKeyInput.buttonPress();

            Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
            assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

            return canReceiveKeyInput;
        }

    public static CanReceiveKeyInput imShowingTheBoostOptions(CapturingViewFramework capturingViewFramework, FakeModel model) {
        CanReceiveKeyInput uiCanReceiveKeyInput = imInTheMenuview(capturingViewFramework, model);
        uiCanReceiveKeyInput.buttonPress();

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertPrecondition("Screen displayed", fakeConfigurationDialogView, isA(FakeConfigurationDialogView.class));

        Option expectedOption = new FakeOption("Boost",true)
                .addHeatingTimeRange(new HeatingTimeRange(HeatingTime.createFromTimeUnit(1, TimeUnit.SECONDS), HeatingTime.createFromTimeUnit(3, TimeUnit.SECONDS), null, HeatingTime.createFromTimeUnit(2, TimeUnit.SECONDS)));

        assertPrecondition("Configured Boost Option ", model.lastConfiguredOption(), is(expectedOption));

        return uiCanReceiveKeyInput;
    }

    public static <T> void assertPrecondition(String message, T actual, Matcher<? super T> matcher)  {
        assertThat("Precondition failed:"+message, actual, matcher);

    }
}
