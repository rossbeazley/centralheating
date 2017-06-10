package uk.co.rossbeazley.centralheating.ui.input;

import uk.co.rossbeazley.centralheating.core.ExternalTimer;
import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NamedPipeKeyInputSpike implements ExternalTimer {

    private List<Observer> observers = new ArrayList<>();

    public static void main(String...args) {
        NamedPipeKeyInputSpike clockwise = new NamedPipeKeyInputSpike(args[0], new CanReceiveKeyInput() {
            @Override
            public void buttonPress() {
                System.err.println("BUTTON PRESS");
            }

            @Override
            public void clockWise() {
                System.err.println("CLOCKWISE");
            }
        });
        clockwise.addObserver(new Observer() {
            @Override
            public void externalTimerOn() {
                System.err.println("ON");
            }

            @Override
            public void externalTimerOff() {
                System.err.println("OFF");
            }
        });
    }

    public NamedPipeKeyInputSpike(String pathToPipe, CanReceiveKeyInput canReceiveKeyInput) {

        final FileReader[] fileReader = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("Making filereader");
                try {
                    fileReader[0] = new FileReader(pathToPipe);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.err.println("Got filereader");
                final FileReader finalFileReader = fileReader[0];

                char c='a';
                do {
                    try {
                        c = (char) finalFileReader.read();
                        System.err.println("read:"+c);
                        switch (c) {
                            case 'c':
                                canReceiveKeyInput.clockWise();
                                break;
                            case 'b':
                                canReceiveKeyInput.buttonPress();
                                break;
                            case 'n':
                                observers.forEach(Observer::externalTimerOn);
                                break;
                            case 'f':
                                observers.forEach(Observer::externalTimerOff);
                                break;
                        }
                        Thread.sleep(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } while (c != 'q');

            }
        }).start();

    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }
}
