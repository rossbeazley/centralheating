package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;
import uk.co.rossbeazley.centralheating.core.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 31/01/17.
 */
public class SelectingAnOptionThatsRenderedAsADialog {

    @Test
    public void
    firstOptionIsSelectedByDefault() throws Exception {


        Model model = new TestHexagonBuilder().withGenericMultiConfigOptions("Option1").build();
        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = imInTheMenuview(capturingViewFramework, model);

        presentationTier.buttonPress();

        assertThat(capturingViewFramework.lastCapturedScreenClass(),is(equalTo(ConfigurationDialogView.class)));
    }



    private PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework, model);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return presentationTier;
    }

}