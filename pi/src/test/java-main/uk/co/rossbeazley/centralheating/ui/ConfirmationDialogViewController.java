package uk.co.rossbeazley.centralheating.ui;

class ConfirmationDialogViewController implements ViewController {

    private final PresentationTier presentationTier;

    public ConfirmationDialogViewController(PresentationTier presentationTier) {
        this.presentationTier = presentationTier;
    }

    @Override
    public void buttonPress() {
        presentationTier.navigationController.presentMenuView(); // wonder if this should be a pop VC off the stack, would work for the general use case... if there is a general use case
    }

    @Override
    public void buttonClockwise() {

    }
}
