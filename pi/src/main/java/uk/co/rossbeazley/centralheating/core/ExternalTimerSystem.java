package uk.co.rossbeazley.centralheating.core;

public class ExternalTimerSystem implements ExternalTimer.Observer {
    private final Option option;
    private State state;
    private ExternalTimer externalTimer;
    private GasBurner gasBurner;
    private ExternalTimer.Observer observer;

    public ExternalTimerSystem(String external, ExternalTimer externalTimer, GasBurner gasBurner) {
        this.externalTimer = externalTimer;
        this.gasBurner = gasBurner;
        this.option = new Option(external);
        this.state = new DisabledOff();
        this.externalTimer.addObserver(this);
    }

    public Option option() {
        return option;
    }

    public void enable(Option option) {
        this.state.enabled();
    }

    public void disable() {
        this.state.disabled();
    }

    @Override
    public void externalTimerOn() {
        this.state.on();
    }

    @Override
    public void externalTimerOff() {
        this.state.off();
    }

    private interface State {
        void enabled();

        void disabled();

        void on();

        void off();
    }

    private class DisabledOff implements State {

        @Override
        public void enabled() {
            state = new EnabledOff();
        }

        @Override
        public void disabled() {
            //no op
        }

        @Override
        public void on() {
            state = new DissabledOn();
        }

        @Override
        public void off() {
            //no op
        }
    }

    private class DissabledOn implements State {
        @Override
        public void enabled() {
            gasBurner.turnOn();
            state = new EnabledOn();
        }

        @Override
        public void disabled() {
            //no op
        }

        @Override
        public void on() {
            //no op
        }

        @Override
        public void off() {
            state = new DisabledOff();
        }
    }

    private class EnabledOff implements State {
        @Override
        public void enabled() {
            //no op
        }

        @Override
        public void disabled() {
            state = new DisabledOff();
        }

        @Override
        public void on() {
            gasBurner.turnOn();
            state = new EnabledOn();
        }

        @Override
        public void off() {
            //no op
        }
    }

    private class EnabledOn implements State {
        @Override
        public void enabled() {
            //no op
        }

        @Override
        public void disabled() {
            // dont change the gas state
            state = new DissabledOn();
        }

        @Override
        public void on() {
            //no op
        }

        @Override
        public void off() {
            gasBurner.turnOff();
            state = new EnabledOff();
        }
    }
}
