package uk.co.rossbeazley.centralheating.gasBurnerRelay;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class GasBurnerGPIORelayTest {

    private final String offValue = "0";
    private final String onValue = "1";
    private GasBurnerGPIORelay gasBurner;
    private Path filePath;

    @Before
    public void generateRandomFilePath() throws Exception {
        filePath = Files.createTempFile("GasBurnerGPIORelayTest", "");
        gasBurner = new GasBurnerGPIORelay(filePath, offValue, onValue);
    }

    @After
    public void removeAnyFiles() {
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void
    turnOnByWritingToFile() throws Exception {

        gasBurner.turnOn();

        assertThat(filePath, isFileWithContents(onValue));
    }


    @Test
    public void
    turnOffByWritingToFile() {

        gasBurner.turnOff();
        assertThat(filePath, isFileWithContents(offValue));
    }

    private Matcher<? super Path> isFileWithContents(String s) {
        return new BaseMatcher<Path>() {
            private List<String> strings;
            private Path item;
            private String failReason = "";

            @Override
            public boolean matches(Object item) {
                this.item = (Path) item;

                try {
                    if (!Files.exists(this.item)) {
                        failReason = "File Not Found";
                        return false;
                    }

                    strings = Files.readAllLines((Path) item);

                    return strings.get(0).equals(s);
                } catch (FileNotFoundException e) {
                    failReason = "FileNotFound";
                } catch (IOException e) {
                    failReason = "IOException " + e.getMessage();
                } catch (IndexOutOfBoundsException e) {
                    failReason = "Empty file";
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("File at path ").appendValue(item).appendText(" to contain ").appendText(s);

            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendText("was ").appendText(failReason);
            }
        };
    }


}