package uk.co.rossbeazley.centralheating.ui.input;

import uk.co.rossbeazley.centralheating.core.ExternalTimer;
import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static uk.co.rossbeazley.centralheating.ui.input.NamedPipeInputChannel.*;

public class NamedPipeKeyInputSpike {

    public NamedPipeKeyInputSpike(final String pathToPipe, final CanReceiveKeyInput canReceiveKeyInput) {

        HashMap<Character, Runnable> parseFunctions = new HashMap<Character, Runnable>() {{
                put('c', canReceiveKeyInput::clockWise);
                put('a', canReceiveKeyInput::antiClockWise);
                put('b', canReceiveKeyInput::buttonPress);
            }};

        ParseFunction parseFunction = c -> parseFunctions.getOrDefault(c, () -> {}).run();

        new NamedPipeInputChannel(pathToPipe, parseFunction);
    }


}
