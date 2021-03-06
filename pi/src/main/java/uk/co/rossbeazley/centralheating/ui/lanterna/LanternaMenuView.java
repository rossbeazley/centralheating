package uk.co.rossbeazley.centralheating.ui.lanterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import uk.co.rossbeazley.centralheating.ui.MenuView;

import java.util.Arrays;

import static com.googlecode.lanterna.gui2.Direction.VERTICAL;

class LanternaMenuView implements MenuView {

    static final int MAIN_LIST_ID = 0x26;

    public static final Runnable NO_ACTION = () -> {};

    private final ActionListBox actionListBox;
    private final Panel panel;
    private final Composite rootView;

    public LanternaMenuView(Composite rootView) {
        this.rootView = rootView;
        panel = new Panel();
        panel.setLayoutManager(new LinearLayout(VERTICAL));

        TerminalSize size = new TerminalSize(27, 6);

        actionListBox = new ActionListBox(size){
            @Override
            public int hashCode() {
                return MAIN_LIST_ID;
            }
        };

        panel.addComponent(actionListBox);

        rootView.setComponent(panel);
    }

    @Override
    public void presentOptions(String... optionStrings) {
        Arrays.asList(optionStrings).forEach(s -> actionListBox.addItem(s,NO_ACTION));
        actionListBox.takeFocus();
    }

    @Override
    public void selectOption(int indexFromZero) {
        actionListBox.setSelectedIndex(indexFromZero);
    }
}
