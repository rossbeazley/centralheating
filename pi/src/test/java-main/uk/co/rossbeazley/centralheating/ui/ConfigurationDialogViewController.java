package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.HeatingTimeRange;
import uk.co.rossbeazley.centralheating.core.Model;

class ConfigurationDialogViewController implements ViewController {


    private PresentationTier presentationTier;
    private Model model;
    private HeatingTimeRange heatingTimeRange;
    private final ConfigurationDialogView view;
    private boolean editing = true;
    private boolean cancelSelected = false;
    private boolean saveSelected = false;

    public ConfigurationDialogViewController(PresentationTier presentationTier, Model model, SelectingHeatingMode.HeatingTime heatingTime, HeatingTimeRange heatingTimeRange, ConfigurationDialogView view) {
        this.presentationTier = presentationTier;
        this.model = model;
        this.heatingTimeRange = heatingTimeRange;
        this.view = view;
        if (heatingTimeRange != null) {
            view.presentChoices(heatingTimeRange.heatingTimeValue().asSecondsString());
            view.highlightOptions();
        }
    }

    @Override
    public void buttonPress() {
        if(saveSelected) {
            model.configure(heatingTimeRange.heatingTimeValue(), new Model.Callback() {
                @Override
                public void OK() {

                }

                @Override
                public void RANGE(HeatingTimeRange heatingTimeRange, SelectingHeatingMode.HeatingTime heatingTime) {

                }
            });

            presentationTier.presentConfirmationDialog();
        }
        else if(cancelSelected) {
            presentationTier.presentMenuView();
        } else {
            this.editing = false;
        }
    }

    @Override
    public void buttonClockwise() {
        if(editing) {
            heatingTimeRange.increment();
            view.presentChoices(heatingTimeRange.heatingTimeValue().asSecondsString());
        } else {

            if(saveSelected) {
                cancelSelected = true;
                saveSelected = false;
                view.highlightCancel();
            } else {
                saveSelected = true;
                view.highlightSave();
            }
        }
    }
}
