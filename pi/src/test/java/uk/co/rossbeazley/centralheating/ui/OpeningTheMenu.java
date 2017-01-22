package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

public class OpeningTheMenu {


    @Test
    public void openMenuFromOverviewScreen() {

//        class ViewFactory

        NavigationController navigationController = new CapturingNavigationController();

        OverviewScreenController controller = new OverviewScreenController(navigationController);

        controller.buttonPress();

        MenuView screenDisplayed = navigationController.topScreen();
        assertThat(screenDisplayed,isA(MenuView.class));
    }


    private interface MenuView {
    }

    private class OverviewScreenController {
        private final NavigationController navigationController;

        public OverviewScreenController(NavigationController navigationController) {
            this.navigationController = navigationController;
        }

        public void buttonPress() {
            this.navigationController.present(new MenuView() {
                
            });
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
