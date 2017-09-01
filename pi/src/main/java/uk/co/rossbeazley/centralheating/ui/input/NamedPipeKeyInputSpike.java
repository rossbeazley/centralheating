package uk.co.rossbeazley.centralheating.ui.input;

import uk.co.rossbeazley.centralheating.core.ExternalTimer;
import uk.co.rossbeazley.centralheating.ui.CanReceiveKeyInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class NamedPipeKeyInputSpike implements ExternalTimer {

    private List<Observer> observers = new ArrayList<>();

    private NamedPipeInputChannel.ParseFunction parseFunction;

    public NamedPipeKeyInputSpike(final String pathToPipe, final CanReceiveKeyInput canReceiveKeyInput) {

        HashMap<Character, Runnable> parseFunctions = new HashMap<>();

        parseFunctions.put('c', canReceiveKeyInput::clockWise);
        parseFunctions.put('a', canReceiveKeyInput::antiClockWise);
        parseFunctions.put('b', canReceiveKeyInput::buttonPress);

        parseFunction = c -> parseFunctions.getOrDefault(c,()->{}).run();

        new NamedPipeInputChannel(pathToPipe, parseFunction);

    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }


}
