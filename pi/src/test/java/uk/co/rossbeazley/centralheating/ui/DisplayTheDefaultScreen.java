package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DisplayTheDefaultScreen {


    @Test
    public void openMenuFromOverviewScreen() {

        CapturingViewFramework capturingViewFramework = new CapturingViewFramework();

        PresentationTier presentationTier = new PresentationTier(capturingViewFramework);


        Class screenDisplayed = capturingViewFramework.lastCapturedScreen();
        assertThat(screenDisplayed,is(equalTo(ScheduleView.class)));
    }


    private interface ScheduleView {
    }

    private class PresentationTier {
        private final ViewFramework viewFramework;

        public PresentationTier(ViewFramework viewFramework) {
            this.viewFramework = viewFramework;

            this.viewFramework.create(ScheduleView.class);
        }

    }

    private interface ViewFramework {
        void create(Class<ScheduleView> view);

    }

    private class CapturingViewFramework implements ViewFramework {

        Class<ScheduleView> presentedView;

        @Override
        public void create(Class<ScheduleView> view) {
            presentedView = view;
        }

        public Class<ScheduleView> lastCapturedScreen() {
            return presentedView;
        }
    }
}
