package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.HeatingTimeRange;
import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.Option;

import java.util.ArrayList;
import java.util.List;

class MenuViewController implements ViewController {
    private final NavigationController navigationController;
    private MenuView menuView;
    private Model model;
    private int selectedIndex;
    private String[] optionsAsString;
    private int closeIndex;
    private List<Option> options;


    private MenuViewController(MenuView menuView, Model model, NavigationController navigationController) {

        this.menuView = menuView;
        this.model = model;

        this.optionsAsString = buildOptionsViewModel();
        this.selectedIndex = 0;

        menuView.presentOptions(optionsAsString);
        menuView.selectOption(selectedIndex);
        this.navigationController = navigationController;
    }

    public static ViewController createMenuViewController(MenuView menuView, Model model, NavigationController navigationController) {
        return new MenuViewController(menuView, model, navigationController);
    }

    private String[] buildOptionsViewModel() {
        List<String> optionsAsString = new ArrayList<>();
        options = this.model.options();
        options.forEach(option -> optionsAsString.add(option.name()));
        optionsAsString.add("Close"); // add close at the end
        this.closeIndex = optionsAsString.indexOf("Close"); // this write operation stinks
        return optionsAsString.toArray(new String[optionsAsString.size()]);
    }

    @Override
    public void buttonPress() {
        if (selectedIndex == closeIndex) {
            navigationController.presentScheduleView();
        } else {
            model.configure(options.get(selectedIndex), new Model.Callback() {
                @Override
                public void OK() {
                    navigationController.presentConfirmationDialog();
                }

                @Override
                public void RANGE(HeatingTimeRange heatingTimeRange) {
                    navigationController.presentConfigurationDialog(heatingTimeRange);
                }

            });

        }
    }

    @Override
    public void buttonClockwise() {
        this.selectedIndex++;
        wrapAroundToStart();
        menuView.selectOption(selectedIndex);
    }

    private void wrapAroundToStart() {
        this.selectedIndex = this.selectedIndex % this.optionsAsString.length;
    }
}
