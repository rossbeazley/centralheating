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

    public PresentationTier(ViewFramework viewFramework, Model model) {
        this.viewFramework = viewFramework;
        this.model = model;

        presentScheduleView();
    }

    private void presentScheduleView() {
        this.viewFramework.create(ScheduleView.class);
        inMenuView = false;
    }

    public void buttonPress() {
        if (inMenuView) {
            if(selectedOption.equals("Close"))
            presentScheduleView();
            else
                presentConfigurationDialog();
        } else {
            presentMenuView();
        }
    }

    private void presentConfigurationDialog() {
        this.viewFramework.create(ConfigurationDialogView.class);
        inMenuView = false;
    }

    private void presentMenuView() {
        inMenuView = true;
        MenuView menuView = this.viewFramework.create(MenuView.class);
        List<String> optionsAsString = new ArrayList<>();
        model.options().forEach(option -> optionsAsString.add(option.name()));

        optionsAsString.add("Close");
        menuView.presentOptions(optionsAsString.toArray(new String[optionsAsString.size()]));

        this.selectedOption = optionsAsString.get(0);

        menuView.selectOption(0);
    }
}
