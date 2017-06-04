package uk.co.rossbeazley.centralheating.ui.lanterna;

import com.googlecode.lanterna.gui2.Composite;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import uk.co.rossbeazley.centralheating.ui.ConfigurationDialogView;
import uk.co.rossbeazley.centralheating.ui.MenuView;
import uk.co.rossbeazley.centralheating.ui.SavedDialogView;
import uk.co.rossbeazley.centralheating.ui.ViewFramework;

import java.util.HashMap;
import java.util.Map;

public class LanternaViewFramework implements ViewFramework {


    private final Composite gui;


    public LanternaViewFramework(Composite gui) {
        this.gui = gui;

    }

    @Override
    public <V,T extends V> T create(Class<V> view) {

        T result = null;


        put(MenuView.class,LanternaMenuView.class);

        Class<T> t = get(view);
        try {
            result = t.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Map<Class,Class> stuff = new HashMap<>();

    public <T extends V, V>void put(Class<V> ifType, Class<T> conType) {
        stuff.put(ifType,conType);
    }

    public <T extends V, V> Class<T> get(Class<V> ifType) {
        return stuff.get(ifType);
    }
}
