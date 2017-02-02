package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class TheMenuViewReturnsToTheScheduleViewWhenClosed {

    @Test
    public void closeIsTheReturnsToScheduleView() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework);

        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreen();
        assertThat(screenDisplayed,is(equalTo(ScheduleView.class)));
    }

    private PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreen();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return presentationTier;
    }

}
