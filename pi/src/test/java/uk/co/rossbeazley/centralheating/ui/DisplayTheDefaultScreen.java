package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DisplayTheDefaultScreen {


    @Test
    public void theScheduleView() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = new PresentationTier(capturingViewFramework);


        Class screenDisplayed = capturingViewFramework.lastCapturedScreen();
        assertThat(screenDisplayed,is(equalTo(ScheduleView.class)));
    }

}
