package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
class CapturingViewFramework implements ViewFramework {

    Class<?> presentedView;

    @Override
    public void create(Class<?> view) {
        presentedView = view;
    }

    public Class<?> lastCapturedScreen() {
        return presentedView;
    }
}
