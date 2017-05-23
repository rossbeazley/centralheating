package uk.co.rossbeazley.centralheating.core;

public interface Clock {
    void addTickReceiver(CanBeTicked ticker);

    public interface CanBeTicked {
        void timeIsAt(long threeOClock);
    }
}
