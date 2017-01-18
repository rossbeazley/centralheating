package uk.co.rossbeazley.centralheating.ui;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

public class OpeningTheMenu {


    @Test @Ignore("Currently being spec-ed")
    public void openMenuFromOverviewScreen() {

        NavigationController navigationController = new CapturingNavigationController();

        OverviewScreenController controller = new OverviewScreenController(navigationController);

        controller.buttonPress();

        MenuView screenDisplayed = navigationController.topScreen();
        assertThat(screenDisplayed,isA(MenuView.class));
    }


    private interface MenuView {
    }

    private class OverviewScreenController {
        public OverviewScreenController(NavigationController navigationController) {
        }

        public void buttonPress() {
        }
    }

    private interface NavigationController {
        void present(MenuView view);

        MenuView topScreen();
    }

    private class CapturingNavigationController implements NavigationController {

        MenuView presentedView;

        @Override
        public void present(MenuView view) {
            presentedView = view;
        }

        @Override
        public MenuView topScreen() {
            return presentedView;
        }
    }
}
