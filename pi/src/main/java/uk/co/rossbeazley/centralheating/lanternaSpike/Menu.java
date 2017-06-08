package uk.co.rossbeazley.centralheating.lanternaSpike;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.gui2.Direction.VERTICAL;

public class Menu {


    public static void main(String... args) throws InterruptedException {
        System.out.println("HELLO WORLD");

        try {
            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
            defaultTerminalFactory.setForceTextTerminal(true);
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
        createView(window, "Menu 1");
        try {
            gui.updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createView(window, "Menu 2");

    }

    public static void createView(Composite rootView, String labelText) {

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(VERTICAL));
        Label label = new Label(labelText);

        panel.addComponent(label);
        TerminalSize size = new TerminalSize(14, 3);
        ActionListBox actionListBox = new ActionListBox(size){
            @Override
            public int hashCode() {
                return 0x26;
            }
        };

        actionListBox.addItem("On", () -> {
        });
        actionListBox.addItem("Off", () -> {
        });
        actionListBox.addItem("External", () -> {
        });
        actionListBox.addItem("Boost", () -> {
        });
        panel.addComponent(actionListBox);


        // Create gui and start gui


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    for (int i = 0; i < actionListBox.getItemCount(); i++) {
                        Thread.sleep(1500);
                        actionListBox.setSelectedIndex(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        rootView.setComponent(panel);

    }
}
