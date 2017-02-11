package uk.co.rossbeazley.centralheating.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by beazlr02 on 23/01/17.
 */
class PresentationTier {
    private final ViewFramework viewFramework;
    private Model model;
    private boolean inMenuView = false;
    private String selectedOption;
    private int selectedIndex;
    private List<String> optionsAsString;
    private int closeIndex;

    public PresentationTier(ViewFramework viewFramework, Model model) {
        this.viewFramework = viewFramework;
        this.model = model;

        presentScheduleView();
    }

    public void buttonPress() {
        if (inMenuView) {
            if (selectedIndex == closeIndex) {
                presentScheduleView();
            } else {
                presentConfigurationDialog();
            }
        } else {
            presentMenuView();
        }
    }

    private void presentScheduleView() {
        this.viewFramework.create(ScheduleView.class);
        inMenuView = false;
    }

    private void presentConfigurationDialog() {
        this.viewFramework.create(ConfigurationDialogView.class);
        inMenuView = false;
    }

    private void presentMenuView() {
        inMenuView = true;
        MenuView menuView = this.viewFramework.create(MenuView.class);
        optionsAsString = new ArrayList<>();
        model.options().forEach(option -> optionsAsString.add(option.name()));

        optionsAsString.add("Close");

        this.closeIndex = optionsAsString.indexOf("Close");
        menuView.presentOptions(optionsAsString.toArray(new String[optionsAsString.size()]));

        selectedIndex = 0;

        this.selectedOption = optionsAsString.get(selectedIndex);

        menuView.selectOption(selectedIndex);
    }
}
