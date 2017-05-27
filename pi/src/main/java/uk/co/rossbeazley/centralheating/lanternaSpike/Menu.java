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
            MultiWindowTextGUI gui = new MultiWindowTextGUI(terminalScreen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            BasicWindow window = new BasicWindow();
            gui.addWindow(window);


            createView(window);

            gui.waitForWindowToClose(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createView(BasePane window) {

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(VERTICAL));
        TerminalSize size = new TerminalSize(14, 4);
        ActionListBox actionListBox = new ActionListBox(size);
        actionListBox.addItem("On", () -> {});
        actionListBox.addItem("Off", () -> {});
        actionListBox.addItem("External", () -> {});
        actionListBox.addItem("Boost", () -> {});
        panel.addComponent(actionListBox);


        // Create gui and start gui

//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    for (int i = 0; i < actionListBox.getItemCount(); i++) {
//                        Thread.sleep(1500);
//                        actionListBox.setSelectedIndex(i);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        window.setComponent(panel);

    }
}
