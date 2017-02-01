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

        imInTheMenuview(capturingViewFramework);

        Class screenDisplayed = capturingViewFramework.lastCapturedScreen();
        assertThat(screenDisplayed,is(equalTo(MenuView.class)));
    }

    private void imInTheMenuview(CapturingViewFramework capturingViewFramework) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework);
        presentationTier.buttonPress();
    }

}
