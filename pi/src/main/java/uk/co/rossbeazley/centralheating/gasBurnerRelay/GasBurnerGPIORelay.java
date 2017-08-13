package uk.co.rossbeazley.centralheating.gasBurnerRelay;

import uk.co.rossbeazley.centralheating.core.GasBurner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GasBurnerGPIORelay implements GasBurner {

    private final String offValue;
    private String onValue;

    @Override
    public void turnOn() {
        write(onValue, this.filePath);
    }

    @Override
    public void turnOff() {
        write(offValue, this.filePath);
    }

    private static void write(String value, Path filePath) {
        try {
            Files.write(filePath, value.getBytes());
        } catch (IOException ignored) { }
    }

    public GasBurnerGPIORelay(Path filePath, String offValue, String onValue) {
        this.filePath = filePath;
        this.offValue = offValue;
        this.onValue = onValue;
    }


    private Path filePath;

}
