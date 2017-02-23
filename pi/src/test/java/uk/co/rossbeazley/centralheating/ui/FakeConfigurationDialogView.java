package uk.co.rossbeazley.centralheating.ui;

import java.util.Arrays;
import java.util.List;

public class FakeConfigurationDialogView implements ConfigurationDialogView {

    public static final Object  NONE = "none",
                                CHOICES = "choices",
                                CANCEL = "cancel",
                                SAVE = "save";

    public List<String> choices;
    private Object highlightedViewWidget = NONE;

    @Override
    public void presentChoices(String... choices) {
        this.choices = Arrays.asList(choices);
    }

    @Override
    public void highlightOptions() {
        this.highlightedViewWidget = CHOICES;
    }

    @Override
    public void highlightCancel() {
        this.highlightedViewWidget = CANCEL;
    }

    @Override
    public void highlightSave() {
        this.highlightedViewWidget = SAVE;
    }

    public Object highlightedViewWidget() {
        return this.highlightedViewWidget;
    }
}
