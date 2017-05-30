package uk.co.rossbeazley.centralheating.ui.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import uk.co.rossbeazley.centralheating.ui.MenuView;

import java.util.Arrays;

import static com.googlecode.lanterna.gui2.Direction.VERTICAL;

public class LanternaMenuView implements MenuView {

    public static final Runnable NO_ACTION = () -> {
    };
    private final ActionListBox actionListBox;

    public LanternaMenuView(Composite rootView) {
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(VERTICAL));

        TerminalSize size = new TerminalSize(14, 3);
        actionListBox = new ActionListBox(size){
            @Override
            public int hashCode() {
                return 0x26;
            }
        };


        panel.addComponent(actionListBox);

        rootView.setComponent(panel);
    }

    @Override
    public void presentOptions(String... optionStrings) {
        Arrays.asList(optionStrings).forEach(s -> actionListBox.addItem(s,NO_ACTION));
    }

    @Override
    public void selectOption(int indexFromZero) {
        actionListBox.setSelectedIndex(indexFromZero);
    }
}
