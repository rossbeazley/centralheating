package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
class CapturingViewFramework implements ViewFramework {

    Class<MenuView> presentedView;

    @Override
    public void create(Class<MenuView> view) {
        presentedView = view;
    }

    public Class<MenuView> lastCapturedScreen() {
        return presentedView;
    }
}
