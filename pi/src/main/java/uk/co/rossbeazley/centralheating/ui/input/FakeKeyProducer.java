package uk.co.rossbeazley.centralheating.ui.input;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FakeKeyProducer {


    static
    public void
    main(String...args) throws Exception {

        String pathToPipe = "/tmp/keys";
        FileWriter pipe = openPipe(pathToPipe);

        System.out.println("writing");


        while (true) {
            String key = new Random().nextBoolean() ? "c" : "b";
            System.out.println(key);
            pipe.write(key);
            pipe.flush();
            Thread.sleep(1000);
        }
    }

    private static FileWriter openPipe(String pathToPipe) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(pathToPipe, true);
            System.out.println("Got filewriter");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileWriter;
    }


}
