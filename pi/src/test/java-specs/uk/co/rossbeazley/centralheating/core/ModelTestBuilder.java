package uk.co.rossbeazley.centralheating.core;

class ModelTestBuilder {

    private GasBurner gasBurner;

    private String onOptionTitle;

    private String offOptionTitle;

    private ExternalTimer externalTimer;
    private String externalTimerTitle;
    private Adapters adapters;
    private String boostTitle;
    private Clock clock;

    public ModelTestBuilder() {
        onOptionTitle = "on";
        offOptionTitle = "off";
        externalTimerTitle = "external";
        boostTitle = "boost";
        gasBurner = new GasBurner();
        clock = new ClockFake();
        externalTimer = new ExternalTimer(ExternalTimer.OFF);
    }

    public ModelTestBuilder withOnTitle(String on) {
        this.onOptionTitle = on;
        return this;
    }


    public ModelTestBuilder withOffTitle(String off) {
        this.offOptionTitle = off;
        return this;
    }

    public ModelTestBuilder withGasBurner(GasBurner gasBurner) {
        this.gasBurner = gasBurner;
        return this;
    }


    public ModelTestBuilder withExternalTimerTitle(String externalTimerTitle) {
        this.externalTimerTitle = externalTimerTitle;
        return this;
    }

    public ModelTestBuilder withExternalTimer(ExternalTimer externalTimer) {
        this.externalTimer = externalTimer;
        return this;
    }


    public Adapters adapters() {
        assert adapters != null;
        return adapters;
    }


    public Model build() {
        ExternalTimerSystem externalTimerSystem = new ExternalTimerSystem(externalTimerTitle, externalTimer, gasBurner);
        BoostSystem boostSystem = new BoostSystem(boostTitle, gasBurner);

        this.adapters = new Adapters(externalTimerTitle, externalTimer, gasBurner, onOptionTitle, offOptionTitle, boostTitle);
        CentralHeatingSystem centralHeatingSystem = new CentralHeatingSystem(onOptionTitle, offOptionTitle, externalTimerSystem, gasBurner, boostSystem);

        clock.addTickReceiver(boostSystem);
        return centralHeatingSystem;
    }

    public ModelTestBuilder withBoostTitle(String boost) {
        this.boostTitle = boost;
        return this;
    }

    public ModelTestBuilder withClock(Clock clock) {
        this.clock = clock;
        return this;
    }

    public class Adapters {
        public final String externalTimerTitle;
        public final ExternalTimer externalTimer;
        public final GasBurner gasBurner;
        public final String onOptionTitle;
        public final String offOptionTitle;
        public final String boostTitle;

        public Adapters(String externalTimerTitle, ExternalTimer externalTimer, GasBurner gasBurner, String onOptionTitle, String offOptionTitle, String boostTitle) {

            this.externalTimerTitle = externalTimerTitle;
            this.externalTimer = externalTimer;
            this.gasBurner = gasBurner;
            this.onOptionTitle = onOptionTitle;
            this.offOptionTitle = offOptionTitle;
            this.boostTitle = boostTitle;
        }
    }

}
