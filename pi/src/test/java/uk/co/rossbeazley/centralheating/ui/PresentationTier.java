package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;

    public PresentationTier(ViewFramework viewFramework) {
        this.viewFramework = viewFramework;

        this.viewFramework.create(ScheduleView.class);
    }

    public void buttonPress() {
        this.viewFramework.create(MenuView.class);
    }
}
