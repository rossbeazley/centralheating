package uk.co.rossbeazley.centralheating.ui.input;

import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.util.HashMap;

import static uk.co.rossbeazley.centralheating.ui.input.NamedPipeInputChannel.*;

public class KY040_controlKnobAdapter {

    public KY040_controlKnobAdapter(final CanReceiveKeyInput canReceiveKeyInput, CharInputChannel namedPipeInputChannel) {

        HashMap<Character, Runnable> parseFunctions = new HashMap<Character, Runnable>() {{
                put('c', canReceiveKeyInput::clockWise);
                put('a', canReceiveKeyInput::antiClockWise);
                put('b', canReceiveKeyInput::buttonPress);
            }};

        ParseFunction parseFunction = c -> parseFunctions.getOrDefault(c, () -> {}).run();

        namedPipeInputChannel.onChar(parseFunction);
    }


}
