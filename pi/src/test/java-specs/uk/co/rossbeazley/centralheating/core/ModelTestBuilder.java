package uk.co.rossbeazley.centralheating.core;

class ModelTestBuilder {

    private GasBurner gasBurner;

    private String onOptionTitle;

    private String offOptionTitle;

    private ExternalTimer externalTimer;
    private String externalTimerTitle;
    private Adapters adapters;

    public ModelTestBuilder() {
        onOptionTitle = "on";
        offOptionTitle = "off";
        externalTimerTitle = "external";
        gasBurner = new GasBurner();
        externalTimer = new ExternalTimer(null);
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
        this.adapters = new Adapters(externalTimerTitle, externalTimer, gasBurner, onOptionTitle, offOptionTitle);
        return new CentralHeatingSystem(onOptionTitle, offOptionTitle, externalTimerSystem, gasBurner);
    }

    public ModelTestBuilder withBoostTitle(String boost) {
        return this;
    }

    public class Adapters {
        public final String externalTimerTitle;
        public final ExternalTimer externalTimer;
        public final GasBurner gasBurner;
        public final String onOptionTitle;
        public final String offOptionTitle;

        public Adapters(String externalTimerTitle, ExternalTimer externalTimer, GasBurner gasBurner, String onOptionTitle, String offOptionTitle) {

            this.externalTimerTitle = externalTimerTitle;
            this.externalTimer = externalTimer;
            this.gasBurner = gasBurner;
            this.onOptionTitle = onOptionTitle;
            this.offOptionTitle = offOptionTitle;
        }
    }
}
