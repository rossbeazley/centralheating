package uk.co.rossbeazley.centralheating.ui.lanterna;

import com.googlecode.lanterna.gui2.Composite;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import uk.co.rossbeazley.centralheating.ui.ConfigurationDialogView;
import uk.co.rossbeazley.centralheating.ui.MenuView;
import uk.co.rossbeazley.centralheating.ui.SavedDialogView;
import uk.co.rossbeazley.centralheating.ui.ViewFramework;

public class LanternaViewFramework implements ViewFramework {


    private final Composite gui;

    public LanternaViewFramework(Composite gui) {
        this.gui = gui;

    }

    @Override
    public <V> V create(Class<V> view) {
        V result = null;
        if (view == ConfigurationDialogView.class) {
//            result = new LanternaConfigurationDialogView();
        } else if (view == SavedDialogView.class) {

        } else if (view == MenuView.class) {
            result = (V) new LanternaMenuView(gui);
        } else {
            //error view
        }

        return result;
    }
}
