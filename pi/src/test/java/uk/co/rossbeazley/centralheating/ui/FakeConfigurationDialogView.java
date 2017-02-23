package uk.co.rossbeazley.centralheating.ui;

import java.util.Arrays;
import java.util.List;

public class FakeConfigurationDialogView implements ConfigurationDialogView {
    public List<String> choices;
    @Override
    public void presentChoices(String...choices) {
        this.choices = Arrays.asList(choices);
    }
}
