package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.*;

import static uk.co.rossbeazley.centralheating.ui.ConfigurationDialogViewController.createConfigurationDialogViewController;
import static uk.co.rossbeazley.centralheating.ui.ConfirmationDialogViewController.createConfirmationDialogViewController;
import static uk.co.rossbeazley.centralheating.ui.MenuViewController.createMenuViewController;
import static uk.co.rossbeazley.centralheating.ui.ScheduleViewController.createScheduleViewController;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;
    private ViewController topViewController;
    private Model model;
    private NavigationController navigationController;

    public PresentationTier(ViewFramework viewFramework, Model model) {
        this.viewFramework = viewFramework;
        this.model = model;
        this.navigationController = new NavigationController(this, this.model, this.viewFramework);
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
        private final Model model;
        private PresentationTier presentationTier;

        public NavigationController(PresentationTier presentationTier, Model model, ViewFramework viewFramework) {
            this.presentationTier = presentationTier;
            this.viewFramework = viewFramework;
            this.model = model;
        }

        void presentScheduleView() {
            ScheduleView view = viewFramework.create(ScheduleView.class);

            ViewController controller = createScheduleViewController(this);

            this.presentationTier.becomeFirstResponder(controller);
        }

        void presentConfigurationDialog(HeatingTimeRange heatingTimeRange, SelectingHeatingMode.HeatingTime heatingTime) {

            ConfigurationDialogView view = viewFramework.create(ConfigurationDialogView.class);

            ViewController controller = createConfigurationDialogViewController(model, heatingTime, heatingTimeRange, view, this);

            this.presentationTier.becomeFirstResponder(controller);

        }

        void presentMenuView() {
            MenuView view = viewFramework.create(MenuView.class);

            ViewController controller = createMenuViewController(view, model, this);

            this.presentationTier.becomeFirstResponder(controller);
        }

        void presentConfirmationDialog() {
            ConfirmationDialogView view = viewFramework.create(ConfirmationDialogView.class);

            ViewController controller = createConfirmationDialogViewController(this);

            this.presentationTier.becomeFirstResponder(controller);
        }
    }
}
