package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;
    private boolean inMenuView = false;

    public PresentationTier(ViewFramework viewFramework) {
        this.viewFramework = viewFramework;

        presentScheduleView();
    }

    private void presentScheduleView() {
        this.viewFramework.create(ScheduleView.class);
        inMenuView = false;
    }

    public void buttonPress() {
        if (inMenuView) {
            presentScheduleView();
        } else {
            presentMenuView();
        }
    }

    private void presentMenuView() {
        inMenuView = true;
        MenuView menuView = this.viewFramework.create(MenuView.class);
        menuView.presentOption("Close");
    }
}
