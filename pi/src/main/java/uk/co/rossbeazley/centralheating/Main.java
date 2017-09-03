package uk.co.rossbeazley.centralheating;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import uk.co.rossbeazley.centralheating.core.*;
import uk.co.rossbeazley.centralheating.gasBurnerRelay.GasBurnerGPIORelay;
import uk.co.rossbeazley.centralheating.ui.PresentationTier;
import uk.co.rossbeazley.centralheating.ui.input.NamedPipeKeyInputSpike;
import uk.co.rossbeazley.centralheating.ui.lanterna.LanternaViewFramework;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {


    public static void main(String... args) throws InterruptedException {

        Runnable textGUI = build(gui -> {

            GasBurner gasBurner = createGasBurner(args[1]);
            ExternalTimer externalTimer = createExternalTimer();
            ExternalTimerSystem externalTimerSystem = createExternalTimerSystem(externalTimer, gasBurner);
            BoostSystem boostSystem = createBoostSystem(gasBurner);

            CentralHeatingSystem centralHeatingSystem = createCoreCentralHeatingSystem(gasBurner, externalTimerSystem, boostSystem);

            createPresentationTier(gui, centralHeatingSystem, args);

        });

        textGUI.run();

    }

    public static ExternalTimer createExternalTimer() {
        List<ExternalTimer.Observer> observers = new ArrayList<>();
        return observers::add;
    }

    public static CentralHeatingSystem createCoreCentralHeatingSystem(GasBurner gasBurner, ExternalTimerSystem externalTimerSystem, BoostSystem boostSystem) {
        return new CentralHeatingSystem("On", "Off", externalTimerSystem, gasBurner, boostSystem);
    }

    public static void createPresentationTier(Composite gui, CentralHeatingSystem centralHeatingSystem, String[] args) {
        PresentationTier presentationTier = new PresentationTier(new LanternaViewFramework(gui), centralHeatingSystem);

        String pathToPipe = args.length>0 ? args[0] : "/tmp/keys";
        NamedPipeKeyInputSpike namedPipeKeyInputSpike = new NamedPipeKeyInputSpike(pathToPipe, presentationTier);
    }

    public static BoostSystem createBoostSystem(GasBurner gasBurner) {
        BoostSystem boostSystem = new BoostSystem("Boost 1 hour", gasBurner);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> boostSystem.timeIsAt(System.currentTimeMillis()), 0, 1, TimeUnit.SECONDS);
        return boostSystem;
    }

    public static ExternalTimerSystem createExternalTimerSystem(ExternalTimer externalTimer, GasBurner gasBurner) {
        return new ExternalTimerSystem("External Timer", externalTimer, gasBurner);
    }

    public static GasBurner createGasBurner(String arg) {
        Path gpioValueOutputPath = FileSystems.getDefault().getPath(arg);
        return new GasBurnerGPIORelay(gpioValueOutputPath, "1", "0");
    }

    public static Runnable build(Consumer<Composite> app) {
        try {
            final WindowBasedTextGUI gui = buildTerminalGui();

            final Window window = new BasicWindow();
            gui.addWindow(window);

            Composite c = wrapWindowInDirtyUpdater(window);

            gui.getGUIThread().invokeLater(() -> app.accept(c));


            return () -> gui.waitForWindowToClose(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ()->{};
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
//        defaultTerminalFactory.setForceTextTerminal(true);
        Terminal terminal = null;
        terminal = defaultTerminalFactory.createTerminal();

        TerminalScreen terminalScreen = new TerminalScreen(terminal);
        terminalScreen.startScreen();
        return new MultiWindowTextGUI(terminalScreen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
    }

}
