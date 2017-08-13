package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class TheMenuViewAntiClockwiseNavigation {


    @Test
    public void
    goBackToTheFirstOption() throws Exception {


        Model model = new TestHexagonBuilder().withGenericConfigOptions("Option1").build();
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, model);


        canReceiveKeyInput.clockWise();
        canReceiveKeyInput.antiClockWise();


        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionSelected, is("Option1"));
    }


    @Test
    public void
    wrapRoundTheSelection() throws Exception {


        Model model = new TestHexagonBuilder().withGenericConfigOptions("Option1").build();
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        CanReceiveKeyInput canReceiveKeyInput = imInTheMenuview(capturingViewFramework, model);


        canReceiveKeyInput.antiClockWise();

        FakeMenuView fakeMenuView = capturingViewFramework.lastCapturedScreenFake();
        assertThat(fakeMenuView.optionSelected, is("Close"));
    }




    private CanReceiveKeyInput imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
        CanReceiveKeyInput canReceiveKeyInput = new PresentationTier(capturingViewFramework, model);
        canReceiveKeyInput.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return canReceiveKeyInput;
    }

}
