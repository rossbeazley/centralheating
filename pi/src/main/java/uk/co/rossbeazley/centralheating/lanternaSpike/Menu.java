package uk.co.rossbeazley.centralheating.lanternaSpike;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.gui2.Direction.VERTICAL;

public class Menu {


    public static void main(String... args) {
        System.out.println("HELLO WORLD");

        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setForceTextTerminal(true);
        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();

            TerminalScreen terminalScreen = new TerminalScreen(terminal);
            terminalScreen.startScreen();

            Panel panel = new Panel();
            panel.setLayoutManager(new LinearLayout(VERTICAL));

            TerminalSize size = new TerminalSize(14, 4);
            ActionListBox actionListBox = new ActionListBox(size);
            BasicWindow window = new BasicWindow();


            actionListBox.addItem("On", window::close);
            actionListBox.addItem("Off", window::close);
            actionListBox.addItem("External", window::close);
            actionListBox.addItem("Boost", window::close);
            panel.addComponent(actionListBox);

            window.setComponent(panel);

            // Create gui and start gui
            MultiWindowTextGUI gui = new MultiWindowTextGUI(terminalScreen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));


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

            gui.addWindowAndWait(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
