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
import com.googlecode.lanterna.gui2.table.TableModel;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.gui2.Direction.VERTICAL;

public class OnOffView {


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
        createPickerView(window, "08:00 on...");
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

        //http://www.asciitable.com/
        char h = getChar(176);
        Label label = new Label("HHH" + h + "CCCCHHHHCCCCHHHHCCCCHHHHCCCCHHHHCCCC");
        panel.addComponent(label);

        Panel component = new Panel();
        panel.addComponent(component);
        // Create gui and start gui


        rootView.setComponent(panel.withBorder(Borders.singleLine(labelText)));

    }
    public static final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };
    private static char getChar(int code) {
        if (code >= 0x80 && code <= 0xFF) {
            return EXTENDED[code - 0x7F];
        }
        return (char) code;
    }


}
