package uk.co.rossbeazley.centralheating.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;
    private ViewController topViewController;
    private Model model;

    public PresentationTier(ViewFramework viewFramework, Model model) {
        this.viewFramework = viewFramework;
        this.model = model;
        presentScheduleView();
    }

    public void buttonPress() {
        topViewController.buttonPress();
    }

    private void presentScheduleView() {
        this.viewFramework.create(ScheduleView.class);
        topViewController = new ScheduleController(this);
    }

    private void presentConfigurationDialog() {
        this.viewFramework.create(ConfigurationDialogView.class);
    }

    private void presentMenuView() {
        MenuView menuView = this.viewFramework.create(MenuView.class);
        topViewController = new MenuController(this,menuView,model);
    }



    private static class MenuController implements ViewController {
        private final PresentationTier presentationTier;
        private Model model;
        private int selectedIndex;
        private String[] optionsAsString;
        private int closeIndex;


        public MenuController(PresentationTier presentationTier, MenuView menuView, Model model) {

            this.presentationTier = presentationTier;
            this.model = model;

            this.optionsAsString = buildOptionsViewModel();
            this.selectedIndex = 0;

            menuView.presentOptions(optionsAsString);
            menuView.selectOption(selectedIndex);
        }

        private String[] buildOptionsViewModel() {
            List<String> optionsAsString = new ArrayList<>();
            this.model.options().forEach(option -> optionsAsString.add(option.name()));
            optionsAsString.add("Close"); // add close at the end
            this.closeIndex = optionsAsString.indexOf("Close"); // this write operation stinks
            return optionsAsString.toArray(new String[optionsAsString.size()]);
        }

        @Override
        public void buttonPress() {
            if (selectedIndex == closeIndex) {
                this.presentationTier.presentScheduleView();
            } else {
                this.presentationTier.presentConfigurationDialog();
            }
        }
    }

    private static class ScheduleController implements ViewController {
        private final PresentationTier presentationTier;

        public ScheduleController(PresentationTier presentationTier) {
            this.presentationTier = presentationTier;
        }

        @Override
        public void buttonPress() {
            this.presentationTier.presentMenuView();
        }
    }
}
