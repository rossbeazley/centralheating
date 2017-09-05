package uk.co.rossbeazley.centralheating.ui.input;

import org.junit.Before;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.io.*;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NamedPipeCharInput {
    TestPipe theTestPipe;

    @Before
    public void setUp() throws Exception {
        theTestPipe = new TestPipe(generateNameOfPipeForWriting());
    }

    @Test
    public void
    clockwise() throws Exception {

        MyParseFunction parseFunction = new MyParseFunction();
        new NamedPipeInputChannel(theTestPipe.path()).onChar(parseFunction);
        theTestPipe.open();
        theTestPipe.write("c");
        theTestPipe.close();
        assertThat(parseFunction.capturedChar, is("c"));

    }


    private String generateNameOfPipeForWriting() {
        return "/tmp" + File.separator + UUID.randomUUID().toString();
    }


    private static class MyParseFunction implements CharInputChannel.ParseFunction {
        private String capturedChar;

        @Override
        public void parse(char c) {
            capturedChar = String.valueOf(c);
        }
    }
}
