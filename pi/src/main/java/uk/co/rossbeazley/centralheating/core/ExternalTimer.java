package uk.co.rossbeazley.centralheating.core;

public interface ExternalTimer {
    void addObserver(Observer observer);

    public interface Observer {
        void externalTimerOn();

        void externalTimerOff();
    }
}
