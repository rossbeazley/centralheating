package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.*;

/**
 * Created by beazlr02 on 23/01/17.
 */
public class PresentationTier {
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

}
