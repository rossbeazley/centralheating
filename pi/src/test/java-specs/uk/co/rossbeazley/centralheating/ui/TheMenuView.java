package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class TheMenuView {


    @Test
    public void displaysCloseOption() throws Exception {
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();
        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, new TestHexagonBuilder().withGenericConfigOptions("Option1").build());
        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItem("Close"));
    }


    @Test
    public void
    selectTheSecondOption() throws Exception {


        Model model = new TestHexagonBuilder().withGenericConfigOptions("Option1").build();
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, model);


        canReceiveKeyInput.clockWise();

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionSelected, is("Close"));
    }


    @Test
    public void
    wrapRoundTheSelection() throws Exception {


        Model model = new TestHexagonBuilder().withGenericConfigOptions("Option1").build();
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, model);


        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.clockWise();

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionSelected, is("Option1"));
    }

    @Test
    public void
    showAMenuOption() throws Exception {

        Model model = new TestHexagonBuilder().withGenericConfigOptions("Option1").build();
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionsDisplayed, hasItems("Option1", "Close"));
    }

    @Test
    public void
    firstOptionIsSelectedByDefault() throws Exception {


        Model model = new TestHexagonBuilder().withGenericConfigOptions("Option1").build();
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, model);

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionSelected, is("Option1"));
    }


    @Test
    public void closeIsTheReturnsToScheduleView() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();
        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, new TestHexagonBuilder().withNoConfigOptions().build());
        canReceiveKeyInput.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat(screenDisplayed,is(equalTo(ScheduleView.class)));
    }

    private CanReceiveKeyInput imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
        CanReceiveKeyInput canReceiveKeyInput = new PresentationTier(capturingViewFramework, model);
        canReceiveKeyInput.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return canReceiveKeyInput;
    }

}
