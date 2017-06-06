package uk.co.rossbeazley.centralheating.ui.input;

import org.junit.Test;
import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.io.*;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NamedPipeCharInput {
    private CollectingCanReceiveKeyInput canReceiveKeyInput = new CollectingCanReceiveKeyInput();


    @Test
    public void
    clockwise() throws Exception {

        String pathToPipe = generateNameOfPipeForWriting();
        createPipe(pathToPipe);
        new NamedPipeKeyInput(pathToPipe, canReceiveKeyInput);
        FileWriter pipe = openPipe(pathToPipe);

        System.out.println("writing");
        pipe.write("c");

        pipe.close();

        Thread.sleep(1000);

        assertThat(canReceiveKeyInput.command, is(CollectingCanReceiveKeyInput.Clockwise));

    }

    private FileWriter openPipe(String pathToPipe) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(pathToPipe, true);
            System.out.println("Got filewriter");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileWriter;
    }

    private void createPipe(String pathToPipe) {
        try {
            System.out.println("Making " + pathToPipe);
            ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/mkfifo", pathToPipe);
            processBuilder.start();
            System.out.println("Started");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateNameOfPipeForWriting() {
        return "/tmp" + File.separator + UUID.randomUUID().toString();
    }

    private static class CollectingCanReceiveKeyInput implements CanReceiveKeyInput {
        private static final String Clockwise = "right";
        private static final String ButtonPress = "button press";
        private static final String None = "none";
        private String command = None;

        @Override
        public void buttonPress() {
            command = ButtonPress;
        }

        @Override
        public void clockWise() {
            command = Clockwise;
        }

        public void reset() {
            command = None;
        }
    }

    private class NamedPipeKeyInput {
        public NamedPipeKeyInput(String pathToPipe, CollectingCanReceiveKeyInput canReceiveKeyInput) {

            final FileReader[] fileReader = {null};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Making filereader");
                    try {
                        fileReader[0] = new FileReader(pathToPipe);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Got filereader");
                    final FileReader finalFileReader = fileReader[0];

                    char c='a';
                    do {
                        try {
                            c = (char) finalFileReader.read();
                            if (c == 'c') {
                                canReceiveKeyInput.clockWise();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } while (c != 'q');

                }
            }).start();

        }
    }
}