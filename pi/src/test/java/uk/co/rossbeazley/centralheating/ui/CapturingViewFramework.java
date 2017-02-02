package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
class CapturingViewFramework implements ViewFramework {

    private final FakeMenuView fakeMenuView = new FakeMenuView();
    Class<?> presentedViewClass;

    @Override
    public FakeMenuView create(Class<?> view) {
        presentedViewClass = view;
        return fakeMenuView;
    }

    public Class<?> lastCapturedScreenClass() {
        return presentedViewClass;
    }

    public FakeMenuView lastCapturedScreenFake() {
        return fakeMenuView;
    }
}
