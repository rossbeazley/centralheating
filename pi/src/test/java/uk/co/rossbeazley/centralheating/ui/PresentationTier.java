package uk.co.rossbeazley.centralheating.ui;

import uk.co.rossbeazley.centralheating.core.FakeModel;
import uk.co.rossbeazley.centralheating.core.Model;
import uk.co.rossbeazley.centralheating.core.Option;

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

    public void clockWise() {
            topViewController.buttonClockwise();
    }

    private void presentScheduleView() {
        this.viewFramework.create(ScheduleView.class);
        topViewController = new ScheduleController(this);
    }

    private void presentConfigurationDialog(HeatingTimeRange heatingTimeRange, SelectingHeatingMode.HeatingTime heatingTime) {
        ConfigurationDialogView view = this.viewFramework.create(ConfigurationDialogView.class);
        topViewController = new ConfigurationDialogViewController(this, heatingTime, view);

    }

    private void presentMenuView() {
        MenuView menuView = this.viewFramework.create(MenuView.class);
        topViewController = new MenuController(this,menuView,model);
    }



    private static class MenuController implements ViewController {
        private final PresentationTier presentationTier;
        private MenuView menuView;
        private Model model;
        private int selectedIndex;
        private String[] optionsAsString;
        private int closeIndex;
        private List<Option> options;


        public MenuController(PresentationTier presentationTier, MenuView menuView, Model model) {

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

    private void presentConfirmationDialog() {
        this.viewFramework.create(ConfirmationDialogView.class);
        this.topViewController = new ViewController() {
            @Override
            public void buttonPress() {
                presentMenuView(); // wonder if this should be a pop VC off the stack, would work for the general use case... if there is a general use case
            }

            @Override
            public void buttonClockwise() {

            }
        };
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

        @Override
        public void buttonClockwise() {

        }
    }

    private static class ConfigurationDialogViewController implements ViewController {


        private PresentationTier presentationTier;
        private final SelectingHeatingMode.HeatingTime heatingTime;
        private final ConfigurationDialogView view;
        private boolean editing = true;
        private boolean cancelSelected = false;

        public ConfigurationDialogViewController(PresentationTier presentationTier, SelectingHeatingMode.HeatingTime heatingTime, ConfigurationDialogView view) {
            this.presentationTier = presentationTier;
            this.heatingTime = heatingTime;
            this.view = view;
            if (heatingTime != null) {
                view.presentChoices(heatingTime.asSecondsString());
                view.highlightOptions();
            }
        }

        @Override
        public void buttonPress() {
            if(cancelSelected) {
                presentationTier.presentMenuView();
            } else {
                this.editing = false;
            }
        }

        @Override
        public void buttonClockwise() {
            if(editing) {
                view.presentChoices(heatingTime.increment().asSecondsString());
            } else {
                cancelSelected = true;
                view.highlightCancel();
            }
        }
    }
}
