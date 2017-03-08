package uk.co.rossbeazley.centralheating.ui;

class ScheduleViewController implements ViewController {
    private final NavigationController navigationController;

    private ScheduleViewController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public static ScheduleViewController createScheduleViewController(NavigationController navigationController) {
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
