package uk.co.rossbeazley.centralheating.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;
    private ButtonPressCommand buttonPressCommand;
    private Model model;

    public PresentationTier(ViewFramework viewFramework, Model model) {
        this.viewFramework = viewFramework;
        this.model = model;
        presentScheduleView();
    }

    public void buttonPress() {
        buttonPressCommand.buttonPress();
    }

    private void presentScheduleView() {
        this.viewFramework.create(ScheduleView.class);
        buttonPressCommand = new ScheduleController(this);
    }

    private void presentConfigurationDialog() {
        this.viewFramework.create(ConfigurationDialogView.class);
    }

    private void presentMenuView() {
        MenuView menuView = this.viewFramework.create(MenuView.class);
        buttonPressCommand = new MenuController(this,menuView,model);
    }



    private static class MenuController implements ButtonPressCommand {
        private final PresentationTier presentationTier;
        private Model model;
        private int selectedIndex;
        private List<String> optionsAsString;
        private int closeIndex;


        public MenuController(PresentationTier presentationTier, MenuView menuView, Model model) {

            this.presentationTier = presentationTier;
            this.model = model;
            optionsAsString = new ArrayList<>();
            this.model.options().forEach(option -> optionsAsString.add(option.name()));
            optionsAsString.add("Close");
            this.closeIndex = optionsAsString.indexOf("Close");
            menuView.presentOptions(optionsAsString.toArray(new String[optionsAsString.size()]));
            selectedIndex = 0;
            menuView.selectOption(selectedIndex);
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

    private static class ScheduleController implements ButtonPressCommand {
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
