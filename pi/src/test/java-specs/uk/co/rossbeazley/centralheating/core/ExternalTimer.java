package uk.co.rossbeazley.centralheating.core;

class ExternalTimer {

    public static final Object OFF = new Object(),
            ON = new Object();
    private Observer externalTimerObserver;
    private Object STATE;

    public ExternalTimer(Object p0) {
        STATE = p0;
    }

    public void addObserver(Observer observer) {
        if (STATE == ON) {
            observer.externalTimerOn();
        } else {
            observer.externalTimerOff();
        }
        this.externalTimerObserver = observer;
    }

    public void turnOn() {
        this.externalTimerObserver.externalTimerOn();
    }

    public void turnOff() {
        this.externalTimerObserver.externalTimerOff();
    }

    public interface Observer {
        void externalTimerOn();

        void externalTimerOff();
    }
}
