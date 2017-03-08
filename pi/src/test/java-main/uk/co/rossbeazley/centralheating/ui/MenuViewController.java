package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.FakeModel;
import uk.co.rossbeazley.centralheating.core.HeatingTimeRange;
import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.Option;

import java.util.ArrayList;
import java.util.List;

class MenuViewController implements ViewController {
    private final PresentationTier presentationTier;
    private MenuView menuView;
    private Model model;
    private int selectedIndex;
    private String[] optionsAsString;
    private int closeIndex;
    private List<Option> options;


    public MenuViewController(PresentationTier presentationTier, MenuView menuView, Model model) {

        this.presentationTier = presentationTier;
        this.menuView = menuView;
        this.model = model;

        this.optionsAsString = buildOptionsViewModel();
        this.selectedIndex = 0;

        menuView.presentOptions(optionsAsString);
        menuView.selectOption(selectedIndex);
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
            this.presentationTier.presentScheduleView();
        } else {
            model.configure(options.get(selectedIndex), new FakeModel.Callback() {
                @Override
                public void OK() {
                    presentationTier.presentConfirmationDialog();
                }

                @Override
                public void RANGE(HeatingTimeRange heatingTimeRange, SelectingHeatingMode.HeatingTime heatingTime) {
                    presentationTier.presentConfigurationDialog( heatingTimeRange,  heatingTime);
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
