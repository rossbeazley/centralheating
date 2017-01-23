package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OpeningTheMenu {


    @Test
    public void openMenuFromOverviewScreen() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = new PresentationTier(capturingViewFramework);
        presentationTier.buttonPress();

        Class screenDisplayed = capturingViewFramework.lastCapturedScreen();
        assertThat(screenDisplayed,is(equalTo(MenuView.class)));
    }


    private interface MenuView {
    }

    private class PresentationTier {
        private final ViewFramework viewFramework;

        public PresentationTier(ViewFramework viewFramework) {
            this.viewFramework = viewFramework;
        }

        public void buttonPress() {
            this.viewFramework.create(MenuView.class);
        }
    }

    private interface ViewFramework {
        void create(Class<MenuView> view);

    }

    private class CapturingViewFramework implements ViewFramework {

        Class<MenuView> presentedView;

        @Override
        public void create(Class<MenuView> view) {
            presentedView = view;
        }

        public Class<MenuView> lastCapturedScreen() {
            return presentedView;
        }
    }
}
