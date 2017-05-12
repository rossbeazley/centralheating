package uk.co.rossbeazley.centralheating.core;

class GasBurner {

    public static final Object OFF = "GAS OFF";
    public static final Object ON = "GAS ON";

    private Object state = OFF;

    public Object state() {
        return state;
    }

    public void turnOn() {
        state = ON;
    }

    public void turnOff() {
        state = OFF;
    }
}
