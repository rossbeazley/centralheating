package uk.co.rossbeazley.centralheating.ui.input;

import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NamedPipeCharInput {
    private CollectingCanReceiveKeyInput canReceiveKeyInput = new CollectingCanReceiveKeyInput();



    public void
    readsRight() throws Exception {

        String pathToPipe = generateNameOfPipeForWriting();
        FileWriter pipe = openPipe(pathToPipe);
        new NamedPipeKeyInput(pathToPipe);

        pipe.write("c");

        assertThat(canReceiveKeyInput.command, is(CollectingCanReceiveKeyInput.Clockwise));

    }

    private FileWriter openPipe(String pathToPipe) {
        return null;
    }

    private String generateNameOfPipeForWriting() {
        return System.getenv("java.io.tmpdir")+File.pathSeparator+UUID.randomUUID().toString();
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
        public NamedPipeKeyInput(String pathToPipe) {
        }
    }
}
