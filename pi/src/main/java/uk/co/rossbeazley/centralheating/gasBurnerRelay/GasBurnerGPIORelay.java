package uk.co.rossbeazley.centralheating.gasBurnerRelay;

import uk.co.rossbeazley.centralheating.core.GasBurner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GasBurnerGPIORelay implements GasBurner {

    @Override
    public void turnOn() {
        write("1", this.filePath);
    }

    @Override
    public void turnOff() {
        write("0", this.filePath);
    }

    private static void write(String value, Path filePath) {
        try {
            Files.write(filePath, value.getBytes());
        } catch (IOException ignored) { }
    }

    private GasBurnerGPIORelay(Path filePath) {
        this.filePath = filePath;
    }


    private Path filePath;

}
