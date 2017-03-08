package uk.co.rossbeazley.centralheating.ui;

class ScheduleViewController implements ViewController {
    private final PresentationTier.NavigationController navigationController;

    private ScheduleViewController(PresentationTier.NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public static ScheduleViewController createScheduleViewController(PresentationTier.NavigationController navigationController) {
        return new ScheduleViewController(navigationController);
    }

    @Override
    public void buttonPress() {
        navigationController.presentMenuView();
    }

    @Override
    public void buttonClockwise() {

    }
}
