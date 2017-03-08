package uk.co.rossbeazley.centralheating.ui;

class ScheduleViewController implements ViewController {
    private final PresentationTier.NavigationController navigationController;

    public ScheduleViewController(PresentationTier.NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    @Override
    public void buttonPress() {
        navigationController.presentMenuView();
    }

    @Override
    public void buttonClockwise() {

    }
}
