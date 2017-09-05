package uk.co.rossbeazley.centralheating.ui.input;

import java.io.FileWriter;
import java.io.IOException;

class TestPipe {
    private FileWriter fileHandle;
    private final String path;

    public TestPipe(String path) {
        this.path = path;
        createPipe(path);
    }

    public void open() {
        fileHandle = openPipe(path);
    }


    public String path() {
        return path;
    }

    public void close() throws Exception {
        fileHandle.close();
        Thread.sleep(1000);
    }

    public void write(String c) throws IOException {
        fileHandle.write(c);
        fileHandle.flush();
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
}
