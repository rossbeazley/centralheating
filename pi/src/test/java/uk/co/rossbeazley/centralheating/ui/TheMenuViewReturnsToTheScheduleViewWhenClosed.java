package uk.co.rossbeazley.centralheating.ui;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
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

        Model model = buildCoreModelWithConfigOptions("Option1");
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems("Option1", "Close"));
    }

    private Model buildCoreModelWithConfigOptions(String option1) {
        return new Model(option1);
    }

    @Test
    public void displaysCloseOption() throws Exception {
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();
        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, buildCoreModelWithConfigOptions("Option1"));
        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItem("Close"));
    }

    @Test
    public void closeIsTheReturnsToScheduleView() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, buildCoreModelWithConfigOptions("Option1"));

        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat(screenDisplayed,is(equalTo(ScheduleView.class)));
    }

    private PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework, model);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return presentationTier;
    }

}
