package uk.co.rossbeazley.centralheating.ui.input;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class NamedPipeInputChannel implements CharInputChannel {
    public NamedPipeInputChannel(String pathToPipe) {
        this.pathToPipe = pathToPipe;
    }

    @Override
    public void onChar(ParseFunction parseFunction) {
        this.parseFunction = parseFunction;
        new Thread(new ReadLoop()).start();
    }

    private final String pathToPipe;

    private ParseFunction parseFunction;



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
