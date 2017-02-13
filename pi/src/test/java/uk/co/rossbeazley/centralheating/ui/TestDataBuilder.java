package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.Option;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestDataBuilder {
    public static Model buildCoreModelWithConfigOptions(String... option1) {
        List<Option> options = new ArrayList<>(option1.length);
        for (String option : option1) {
            options.add(new Option(option));
        }
        return new Model(options);
    }

    public static Model buildAnyCoreModel() {
        return buildCoreModelWithConfigOptions("Option1");
    }

    static PresentationTier imInTheMenuview(CapturingViewFramework capturingViewFramework, Model model) {
        PresentationTier presentationTier = new PresentationTier(capturingViewFramework, model);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreenClass();
        assertThat("Precondition failed, screen displayed",screenDisplayed,is(equalTo(MenuView.class)));

        return presentationTier;
    }
}
