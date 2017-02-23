package uk.co.rossbeazley.centralheating.ui;

import org.hamcrest.Matcher;
import uk.co.rossbeazley.centralheating.core.FakeModel;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UIContext {
        static PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
            PresentationTier presentationTier = new PresentationTier(capturingViewFramework, model);
            presentationTier.buttonPress();

            Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
            assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

            return presentationTier;
        }

    public static PresentationTier imShowingTheBoostOptions(CapturingViewFramework capturingViewFramework, FakeModel model) {
        PresentationTier uiPresentationTier = imInTheMenuview(capturingViewFramework, model);
        uiPresentationTier.buttonPress();

        FakeConfigurationDialogView fakeConfigurationDialogView = capturingViewFramework.lastCapturedScreenFakeIfIsClass(ConfigurationDialogView.class);
        assertPrecondition("Screen displayed", fakeConfigurationDialogView, isA(FakeConfigurationDialogView.class));
        assertPrecondition("Configured Option Name", model.lastOptionConfigured().name(), is("Boost"));
        assertPrecondition("Default Value", model.lastOptionConfigured().defaultValue(), is(notNullValue()));
        return uiPresentationTier;
    }

    public static <T> void assertPrecondition(String message, T actual, Matcher<? super T> matcher)  {
        assertThat("Precondition failed:"+message, actual, matcher);

    }
}
