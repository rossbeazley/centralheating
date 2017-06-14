package uk.co.rossbeazley.centralheating.ui.lanterna;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Composite;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.Main;
import uk.co.rossbeazley.centralheating.lanternaSpike.MenuFirmingTest;
import uk.co.rossbeazley.centralheating.ui.MenuView;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static uk.co.rossbeazley.centralheating.lanternaSpike.MenuFirmingTest.findComponentInCompositeByHashCode;

public class LanternaMenuViewIntegrationTest {



    @Test
    public void
    highlightsTheRow() throws Exception {

        Main.build(gui -> {
            LanternaViewFramework lanternaViewFramework;

            lanternaViewFramework = new LanternaViewFramework(gui);

            MenuView lanternaMenuView = lanternaViewFramework.create(MenuView.class);
            lanternaMenuView.presentOptions("one", "two", "three", "four");
            lanternaMenuView.selectOption(2);

            ActionListBox actual = (ActionListBox) findComponentInCompositeByHashCode(gui, LanternaMenuView.MAIN_LIST_ID);

            assertThat(actual.getSelectedItem().toString(),is("three"));
            assertThat(actual.isFocused(),is(true));

        });


    }
}