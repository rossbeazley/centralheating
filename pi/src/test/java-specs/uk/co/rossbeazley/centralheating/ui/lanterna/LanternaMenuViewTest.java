package uk.co.rossbeazley.centralheating.ui.lanterna;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Composite;
import com.googlecode.lanterna.gui2.Panel;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.lanternaSpike.Menu;
import uk.co.rossbeazley.centralheating.lanternaSpike.MenuFirmingTest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;
import static uk.co.rossbeazley.centralheating.lanternaSpike.MenuFirmingTest.findComponentInCompositeByHashCode;

public class LanternaMenuViewTest {

    private static final int MAIN_LIST_ID = 0x26;

    @Test
    public void
    showsTheOptions() throws Exception {

        Composite window = new MenuFirmingTest.CapturingLanternaComposite();

        new LanternaMenuView(window).presentOptions("one","two","three","four");

        ActionListBox actual = (ActionListBox) findComponentInCompositeByHashCode(window, MAIN_LIST_ID);


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

        ActionListBox actual = (ActionListBox) findComponentInCompositeByHashCode(window, MAIN_LIST_ID);

        assertThat(actual.getSelectedItem().toString(),is("three"));

    }
}