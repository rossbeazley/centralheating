package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
class CapturingViewFramework implements ViewFramework {

    private final FakeMenuView fakeMenuView = new FakeMenuView();
    private final FakeConfigurationDialogView fakeConfigurationDialogView = new FakeConfigurationDialogView();
    private final FakeConfirmationDialogView fakeConfirmationDialogView = new FakeConfirmationDialogView();
    Class<?> presentedViewClass;
    private Object lastCapturedScreenFake;

    @Override
    public <V> V create(Class<V> clazz) {
        presentedViewClass = clazz;
        V result = null;
        if ( clazz.equals(MenuView.class) ) {
            result = (V) fakeMenuView;
        } else if ( clazz.equals(ConfigurationDialogView.class) ){
            result = (V) fakeConfigurationDialogView;
        } else if( clazz.equals(ConfirmationDialogView.class ) ) {
            result = (V) fakeConfirmationDialogView;
        }

        this.lastCapturedScreenFake = result;
        return result;
    }

    public Class<?> lastCapturedScreenClass() {
        return presentedViewClass;
    }

    public FakeMenuView lastCapturedScreenFake() {
        return fakeMenuView;
    }

    public <S extends T, T> S lastCapturedScreenFakeIfIsClass(Class<T> clazz) {
        S result = null;

        if ( clazz.equals(presentedViewClass) ) {
            result = (S) lastCapturedScreenFake;
        }

        return result;
    }
}
