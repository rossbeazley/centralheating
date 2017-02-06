package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class TheMenuViewReturnsToTheScheduleViewWhenClosed {


    @Test
    public void
    showAMenuOption() throws Exception {

        //model present an option (UI will add the close option)
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();
        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework);

        //MODEL NEEDS TO BE MADE


        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems("Option1", "Close"));
    }

    @Test
    public void displaysCloseOption() throws Exception {
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();
        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework);
        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionDisplayed, is("Close"));
    }

    @Test
    public void closeIsTheReturnsToScheduleView() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework);

        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat(screenDisplayed,is(equalTo(ScheduleView.class)));
    }

    private PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return presentationTier;
    }

}
