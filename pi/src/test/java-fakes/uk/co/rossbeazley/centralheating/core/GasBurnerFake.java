package uk.co.rossbeazley.centralheating.core;

class GasBurnerFake implements GasBurner {

    public static final Object OFF = "GAS OFF";
    public static final Object ON = "GAS ON";

    private Object state = OFF;

    public Object state() {
        return state;
    }

    @Override
    public void turnOn() {
        state = ON;
    }

    @Override
    public void turnOff() {
        state = OFF;
    }
}
