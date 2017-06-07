package uk.co.rossbeazley.centralheating;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import uk.co.rossbeazley.centralheating.core.*;
import uk.co.rossbeazley.centralheating.ui.PresentationTier;
import uk.co.rossbeazley.centralheating.ui.input.NamedPipeKeyInputSpike;
import uk.co.rossbeazley.centralheating.ui.lanterna.LanternaViewFramework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String... args) throws InterruptedException {

        try {
            WindowBasedTextGUI gui = buildTerminalGui();
            Window window = new BasicWindow();

            gui.addWindow(window);
            gui.getGUIThread().invokeLater(() -> {

                Composite c = wrapWindowInDirtyUpdater(window);
                gotoViewFramework(c);

            });


            gui.waitForWindowToClose(window);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Composite wrapWindowInDirtyUpdater(Window window) {
        return new Composite() {
                        @Override
                        public Component getComponent() {
                            return window.getComponent();
                        }

                        @Override
                        public void setComponent(Component component) {
                            window.setComponent(component);
                            try {
                                window.getTextGUI().updateScreen();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
    }

    public static WindowBasedTextGUI buildTerminalGui() throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setForceTextTerminal(true);
        Terminal terminal = null;
        terminal = defaultTerminalFactory.createTerminal();

        TerminalScreen terminalScreen = new TerminalScreen(terminal);
        terminalScreen.startScreen();
        return new MultiWindowTextGUI(terminalScreen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
    }

    /**
     * Contract requires the active window to have a component set on it then the gui told to update screen
     *
     * @param gui
     * @throws IOException
     * @throws InterruptedException
     */
    private static void gotoViewFramework(Composite gui) {

        List<ExternalTimer.Observer> observers = new ArrayList<>();

        ExternalTimer externalTimer = observers::add;


        GasBurner gasBurner = new GasBurner() {
            @Override
            public void turnOn() {
                System.err.println("GASS BURNER ON");
            }

            @Override
            public void turnOff() {
                System.err.println("GASS BURNER OFF");

            }
        };
        ExternalTimerSystem externalTimerSystem = new ExternalTimerSystem("External Timer", externalTimer, gasBurner);
        BoostSystem boostSystem = new BoostSystem("Boost 1 hour", gasBurner);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> boostSystem.timeIsAt(System.currentTimeMillis()),0,1, TimeUnit.SECONDS);

        CentralHeatingSystem centralHeatingSystem = new CentralHeatingSystem("On", "Off", externalTimerSystem, gasBurner, boostSystem);

        PresentationTier presentationTier = new PresentationTier(new LanternaViewFramework(gui), centralHeatingSystem);

        NamedPipeKeyInputSpike namedPipeKeyInputSpike = new NamedPipeKeyInputSpike("/tmp/keys", presentationTier);
        namedPipeKeyInputSpike.addObserver(new ExternalTimer.Observer() {
            @Override
            public void externalTimerOn() {
                observers.forEach(ExternalTimer.Observer::externalTimerOn);
            }

            @Override
            public void externalTimerOff() {
                observers.forEach(ExternalTimer.Observer::externalTimerOff);
            }
        });

    }
}
