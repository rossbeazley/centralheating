package uk.co.rossbeazley.centralheating.lanternaSpike;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Composite;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.gui2.Direction.VERTICAL;

public class TempRange {


    public static void main(String... args) throws InterruptedException {
        System.out.println("HELLO WORLD");

        try {
            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            //defaultTerminalFactory.setForceTextTerminal(true);
            Terminal terminal = null;
            terminal = defaultTerminalFactory.createTerminal();

            TerminalScreen terminalScreen = new TerminalScreen(terminal);
            terminalScreen.startScreen();
            WindowBasedTextGUI gui = new MultiWindowTextGUI(terminalScreen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            Window window = new BasicWindow();

            gui.addWindow(window);
            gui.getGUIThread().invokeLater(() -> {

                    gotoViewFramework(gui);

            });


            gui.waitForWindowToClose(window);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Contract requires the active window to have a component set on it then the gui told to update screen
     *
     * @param gui
     * @throws IOException
     * @throws InterruptedException
     */
    private static void gotoViewFramework(WindowBasedTextGUI gui) {

        Composite window = gui.getActiveWindow();
        createListView(window, "timer");
        //createPickerView(window, "08:00 on...");
        try {
            gui.updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void createPickerView(Composite rootView, String labelText) {

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(VERTICAL));

        TerminalSize size = new TerminalSize(24, 9);

        panel.setSize(size);

        Label label = new Label("off at 09:00");
        panel.addComponent(label);

        Panel component = new Panel();
        panel.addComponent(component);
        // Create gui and start gui


        rootView.setComponent(panel.withBorder(Borders.singleLine(labelText)));

    }

    public static void createListView(Composite rootView, String labelText) {

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(VERTICAL));

        TerminalSize size = new TerminalSize(24, 9);

        panel.setSize(size);

        Table<String> table = new Table<String>("On", "Off");
        panel.addComponent(table);

        table.getTableModel().addRow("08:00","---");

        table.getTableModel().addRow("---","---");
        table.getTableModel().addRow("-->","09:00");
        table.getTableModel().addRow("---","---");
        table.getTableModel().addRow("12:00","<--");
        table.getTableModel().addRow("---","---");
        table.getTableModel().addRow("-->","21:00");

        // Create gui and start gui

        rootView.setComponent(panel.withBorder(Borders.singleLine(labelText)));

    }
}
