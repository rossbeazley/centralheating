package uk.co.rossbeazley.centralheating.lanternaSpike;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.ComponentRenderer;
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
import com.googlecode.lanterna.gui2.table.TableModel;
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

        Table<String> table = new Table<String>("Time", "On/Off");
        panel.addComponent(table);

        TableModel<String> tableModel = table.getTableModel();
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 10) {

                String time = "" + (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m);

                String onOff = h > 8 && h < 16 ? "off" : "on";
                tableModel.addRow(time, onOff);
            }
        }

        table.setVisibleRows(14);

        table.setTableCellRenderer(new TempTableCellRenderer<>());

        // Create gui and start gui

        rootView.setComponent(panel.withBorder(Borders.singleLine(labelText)));
        new Thread(() -> {
            int r = 9;
            while (true) {
                table.setSelectedRow(++r);
                table.invalidate();

                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
