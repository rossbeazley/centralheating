package uk.co.rossbeazley.centralheating.ui;

public interface ConfigurationDialogView {
    void presentChoices(String... choices);

    void highlightOptions();

    void highlightCancel();

    void highlightSave();
}
