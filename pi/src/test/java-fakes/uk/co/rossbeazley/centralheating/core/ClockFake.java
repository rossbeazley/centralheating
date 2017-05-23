package uk.co.rossbeazley.centralheating.core;

import java.util.ArrayList;
import java.util.List;

public class ClockFake implements Clock {

    private List<CanBeTicked> tickers;

    public ClockFake() {
        tickers = new ArrayList<>();
    }

    @Override
    public void addTickReceiver(CanBeTicked ticker) {
        this.tickers.add(ticker);
    }

    public void timeIsAt(long threeOClock) {
        tickers.forEach(canBeTicked -> canBeTicked.timeIsAt(threeOClock));
    }

}
