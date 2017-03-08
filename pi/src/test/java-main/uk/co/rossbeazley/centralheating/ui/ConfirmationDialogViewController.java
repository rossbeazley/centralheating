package uk.co.rossbeazley.centralheating.ui;

class ConfirmationDialogViewController implements ViewController {

    private final PresentationTier.NavigationController navigationController;

    public ConfirmationDialogViewController(PresentationTier.NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    @Override
    public void buttonPress() {
        navigationController.presentMenuView(); // wonder if this should be a pop VC off the stack, would work for the general use case... if there is a general use case
    }

    @Override
    public void buttonClockwise() {

    }
}
