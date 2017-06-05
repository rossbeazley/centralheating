package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OpeningTheMenu {


    @Test
    public void openMenuFromScheduleView() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = new PresentationTier(capturingViewFramework, new TestHexagonBuilder().withAnyOptions().build());
        canReceiveKeyInput.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat(screenDisplayed,is(equalTo(MenuView.class)));
    }


}
