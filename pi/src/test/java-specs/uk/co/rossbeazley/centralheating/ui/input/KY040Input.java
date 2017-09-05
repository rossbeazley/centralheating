package uk.co.rossbeazley.centralheating.ui.input;

import org.junit.Before;
import org.junit.Test;
import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KY040Input {
    TestPipe theTestPipe;

    @Before
    public void setUp() throws Exception {
        theTestPipe = new TestPipe(generateNameOfPipeForWriting());
    }

    @Test
    public void
    clockwise() throws Exception {
        CollectingCanReceiveKeyInput canReceiveKeyInput = new CollectingCanReceiveKeyInput();
        new KY040_controlKnobAdapter(canReceiveKeyInput, new NamedPipeInputChannel(theTestPipe.path()));
        theTestPipe.open();
        theTestPipe.write("c");
        theTestPipe.close();
        assertThat(canReceiveKeyInput.command, is(CollectingCanReceiveKeyInput.Clockwise));

    }


    @Test
    public void
    antiClockwise() throws Exception {

        CollectingCanReceiveKeyInput canReceiveKeyInput = new CollectingCanReceiveKeyInput();
        new KY040_controlKnobAdapter(canReceiveKeyInput, new NamedPipeInputChannel(theTestPipe.path()));
        theTestPipe.open();
        theTestPipe.write("a");
        theTestPipe.close();
        assertThat(canReceiveKeyInput.command, is(CollectingCanReceiveKeyInput.Anticlockwise));

    }

    @Test
    public void
    button() throws Exception {

        CollectingCanReceiveKeyInput canReceiveKeyInput = new CollectingCanReceiveKeyInput();
        new KY040_controlKnobAdapter(canReceiveKeyInput, new NamedPipeInputChannel(theTestPipe.path()));
        theTestPipe.open();
        theTestPipe.write("b");
        theTestPipe.close();
        assertThat(canReceiveKeyInput.command, is(CollectingCanReceiveKeyInput.ButtonPress));

    }

    private String generateNameOfPipeForWriting() {
        return "/tmp" + File.separator + UUID.randomUUID().toString();
    }

    private static class CollectingCanReceiveKeyInput implements CanReceiveKeyInput {
        private static final String Clockwise = "right";
        private static final String Anticlockwise = "left";
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

        @Override
        public void antiClockWise() {
            command = Anticlockwise;
        }

        public void reset() {
            command = None;
        }
    }

}
