package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.*;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;
    private ViewController topViewController;
    private Model model;

    public PresentationTier(ViewFramework viewFramework, Model model) {
        this.viewFramework = viewFramework;
        this.model = model;
        presentScheduleView();
    }

    public void buttonPress() {
        topViewController.buttonPress();
    }

    public void clockWise() {
            topViewController.buttonClockwise();
    }

    void presentScheduleView() {
        this.viewFramework.create(ScheduleView.class);
        topViewController = new ScheduleViewController(this);
    }

    void presentConfigurationDialog(HeatingTimeRange heatingTimeRange, SelectingHeatingMode.HeatingTime heatingTime) {
        ConfigurationDialogView view = this.viewFramework.create(ConfigurationDialogView.class);
        topViewController = new ConfigurationDialogViewController(this,model, heatingTime, heatingTimeRange,view);

    }

    void presentMenuView() {
        MenuView menuView = this.viewFramework.create(MenuView.class);
        topViewController = new MenuViewController(this,menuView,model);
    }


    void presentConfirmationDialog() {
        this.viewFramework.create(ConfirmationDialogView.class);
        this.topViewController = new ConfirmationDialogViewController(PresentationTier.this);
    }

    private static class ConfirmationDialogViewController implements ViewController {

        private final PresentationTier presentationTier;

        public ConfirmationDialogViewController(PresentationTier presentationTier) {
            this.presentationTier = presentationTier;
        }

        @Override
        public void buttonPress() {
            presentationTier.presentMenuView(); // wonder if this should be a pop VC off the stack, would work for the general use case... if there is a general use case
        }

        @Override
        public void buttonClockwise() {

        }
    }
}
