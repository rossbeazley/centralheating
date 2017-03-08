package uk.co.rossbeazley.centralheating.ui;

class ScheduleViewController implements ViewController {
    private final PresentationTier presentationTier;

    public ScheduleViewController(PresentationTier presentationTier) {
        this.presentationTier = presentationTier;
    }

    @Override
    public void buttonPress() {
        this.presentationTier.presentMenuView();
    }

    @Override
    public void buttonClockwise() {

    }
}
