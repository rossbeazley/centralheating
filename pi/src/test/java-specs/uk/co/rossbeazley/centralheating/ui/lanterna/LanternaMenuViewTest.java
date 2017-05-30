package uk.co.rossbeazley.centralheating.ui.lanterna;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Composite;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.lanternaSpike.MenuFirmingTest;
import uk.co.rossbeazley.centralheating.ui.MenuView;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;
import static uk.co.rossbeazley.centralheating.lanternaSpike.MenuFirmingTest.findComponentInCompositeByHashCode;

public class LanternaMenuViewTest {

    @Test
    public void
    showsTheOptions() throws Exception {

        Composite window = new MenuFirmingTest.CapturingLanternaComposite();

        new LanternaViewFramework(window).create(MenuView.class).presentOptions("one", "two", "three", "four");

        ActionListBox actual = (ActionListBox) findComponentInCompositeByHashCode(window, LanternaMenuView.MAIN_LIST_ID);


        List<String> list = actual.getItems()
                                    .stream()
                                    .map(Runnable::toString)
                                    .collect(Collectors.toList());
        assertThat(list,hasItems("one","two","three","four"));

    }

    @Test
    public void
    highlightsTheRow() throws Exception {
        Composite window = new MenuFirmingTest.CapturingLanternaComposite();

        LanternaMenuView lanternaMenuView = new LanternaMenuView(window);
        lanternaMenuView.presentOptions("one", "two", "three", "four");
        lanternaMenuView.selectOption(2);

        ActionListBox actual = (ActionListBox) findComponentInCompositeByHashCode(window, LanternaMenuView.MAIN_LIST_ID);

        assertThat(actual.getSelectedItem().toString(),is("three"));

    }
}