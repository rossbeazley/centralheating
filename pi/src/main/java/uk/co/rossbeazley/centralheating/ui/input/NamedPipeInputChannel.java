package uk.co.rossbeazley.centralheating.ui.input;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class NamedPipeInputChannel {
    public interface ParseFunction {
        void parse(char c);
    }
    private final String pathToPipe;

    private final ParseFunction parseFunction;


    public NamedPipeInputChannel(String pathToPipe, ParseFunction parseFunction) {
        this.pathToPipe = pathToPipe;
        this.parseFunction = parseFunction;

        new Thread(new ReadLoop()).start();
    }

    private class ReadLoop implements Runnable {
        @Override
        public void run() {

            try (FileReader fileReader = new FileReader(pathToPipe)) {

                char c = 0;
                do {
                    try {
                        c = (char) fileReader.read();
                        //System.err.println("read:"+c);
                        parseFunction.parse(c);
                        Thread.sleep(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } while (c != 'q');

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
