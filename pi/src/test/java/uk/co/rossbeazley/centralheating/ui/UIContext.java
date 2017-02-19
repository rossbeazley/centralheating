package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
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
}
