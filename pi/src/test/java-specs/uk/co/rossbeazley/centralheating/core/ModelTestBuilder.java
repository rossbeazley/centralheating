package uk.co.rossbeazley.centralheating.core;

class ModelTestBuilder {

    private TheExternalTimer.GasBurner gasBurner;

    private String onOptionTitle;

    private String offOptionTitle;

    private TheExternalTimer.ExternalTimer externalTimer;
    private String externalTimerTitle;
    private Adapters adapters;

    public ModelTestBuilder() {
        onOptionTitle = "on";
        offOptionTitle = "off";
        externalTimerTitle = "external";
        gasBurner = new TheExternalTimer.GasBurner();
        externalTimer = new TheExternalTimer.ExternalTimer(null);
    }

    public ModelTestBuilder withOnTitle(String on) {
        this.onOptionTitle = on;
        return this;
    }


    public ModelTestBuilder withOffTitle(String off) {
        this.offOptionTitle = off;
        return this;
    }

    public ModelTestBuilder withGasBurner(TheExternalTimer.GasBurner gasBurner) {
        this.gasBurner = gasBurner;
        return this;
    }


    public ModelTestBuilder withExternalTimerTitle(String externalTimerTitle) {
        this.externalTimerTitle = externalTimerTitle;
        return this;
    }

    public ModelTestBuilder withExternalTimer(TheExternalTimer.ExternalTimer externalTimer) {
        this.externalTimer = externalTimer;
        return this;
    }


    public Adapters adapters() {
        assert adapters != null;
        return adapters;
    }


    public Model build() {
        TheExternalTimer.ExternalTimerSystem externalTimerSystem = new TheExternalTimer.ExternalTimerSystem(externalTimerTitle, externalTimer, gasBurner);
        this.adapters = new Adapters(externalTimerTitle, externalTimer, gasBurner, onOptionTitle, offOptionTitle);
        return new TheExternalTimer.CentralHeatingSystem(onOptionTitle, offOptionTitle, externalTimerSystem, gasBurner);
    }

    public class Adapters {
        public final String externalTimerTitle;
        public final TheExternalTimer.ExternalTimer externalTimer;
        public final TheExternalTimer.GasBurner gasBurner;
        public final String onOptionTitle;
        public final String offOptionTitle;

        public Adapters(String externalTimerTitle, TheExternalTimer.ExternalTimer externalTimer, TheExternalTimer.GasBurner gasBurner, String onOptionTitle, String offOptionTitle) {

            this.externalTimerTitle = externalTimerTitle;
            this.externalTimer = externalTimer;
            this.gasBurner = gasBurner;
            this.onOptionTitle = onOptionTitle;
            this.offOptionTitle = offOptionTitle;
        }
    }
}
