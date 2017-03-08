package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.*;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;
    private ViewController topViewController;
    private Model model;
    NavigationController navigationController;

    public PresentationTier(ViewFramework viewFramework, Model model) {
        this.viewFramework = viewFramework;
        this.model = model;
        this.navigationController = new NavigationController(this);
        navigationController.presentScheduleView();
    }

    public void buttonPress() {
        topViewController.buttonPress();
    }

    public void clockWise() {
        topViewController.buttonClockwise();
    }

    void becomeFirstResponder(ViewController topViewController) {
        this.topViewController = topViewController;
    }

    static class NavigationController {

        private final ViewFramework viewFramework;
        private PresentationTier presentationTier;

        public NavigationController(PresentationTier presentationTier) {
            this.presentationTier = presentationTier;
            viewFramework = this.presentationTier.viewFramework;
        }

        void presentScheduleView() {
            viewFramework.create(ScheduleView.class);
            this.presentationTier.becomeFirstResponder(new ScheduleViewController(this.presentationTier));
        }

        void presentConfigurationDialog(HeatingTimeRange heatingTimeRange, SelectingHeatingMode.HeatingTime heatingTime) {
            ConfigurationDialogView view = viewFramework.create(ConfigurationDialogView.class);
            this.presentationTier.becomeFirstResponder(new ConfigurationDialogViewController(this.presentationTier.model, heatingTime, heatingTimeRange, view, this.presentationTier.navigationController));

        }

        void presentMenuView() {
            MenuView menuView = viewFramework.create(MenuView.class);
            this.presentationTier.becomeFirstResponder(new MenuViewController(this.presentationTier, menuView, this.presentationTier.model));
        }

        void presentConfirmationDialog() {
            viewFramework.create(ConfirmationDialogView.class);
            this.presentationTier.becomeFirstResponder(new ConfirmationDialogViewController(this.presentationTier));
        }
    }
}
