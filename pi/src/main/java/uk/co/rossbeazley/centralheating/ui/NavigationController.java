package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.HeatingTimeRange;
import uk.co.rossbeazley.centralheating.core.Model;

import static uk.co.rossbeazley.centralheating.ui.ConfigurationDialogViewController.createConfigurationDialogViewController;
import static uk.co.rossbeazley.centralheating.ui.ConfirmationDialogViewController.createConfirmationDialogViewController;
import static uk.co.rossbeazley.centralheating.ui.MenuViewController.createMenuViewController;
import static uk.co.rossbeazley.centralheating.ui.ScheduleViewController.createScheduleViewController;

class NavigationController {

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

    void presentConfigurationDialog(HeatingTimeRange heatingTimeRange) {

        ConfigurationDialogView view = viewFramework.create(ConfigurationDialogView.class);

        ViewController controller = createConfigurationDialogViewController(model, heatingTimeRange, view, this);

        this.presentationTier.becomeFirstResponder(controller);

    }

    void presentMenuView() {
        MenuView view = viewFramework.create(MenuView.class);

        ViewController controller = createMenuViewController(view, model, this);

        this.presentationTier.becomeFirstResponder(controller);
    }

    void presentConfirmationDialog() {
        SavedDialogView view = viewFramework.create(SavedDialogView.class);

        ViewController controller = createConfirmationDialogViewController(this);

        this.presentationTier.becomeFirstResponder(controller);
    }
}
