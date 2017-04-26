package uk.co.rossbeazley.centralheating.core;

class ModelTestBuilder {

    private ModelTest.GasBurner gasBurner;

    private String onOptionTitle;

    private String offOptionTitle;

    private ModelTest.ExternalTimer externalTimer;
    private String externalTimerTitle;

    public ModelTestBuilder() {
        onOptionTitle = "on";
        offOptionTitle = "off";
        externalTimerTitle = "external";
        gasBurner = new ModelTest.GasBurner();
        externalTimer = new ModelTest.ExternalTimer(null);
    }

    public ModelTestBuilder withOnTitle(String on) {
        this.onOptionTitle = on;
        return this;
    }


    public ModelTestBuilder withOffTitle(String off) {
        this.offOptionTitle = off;
        return this;
    }

    public ModelTestBuilder withGasBurner(ModelTest.GasBurner gasBurner) {
        this.gasBurner = gasBurner;
        return this;
    }


    public ModelTestBuilder withExternalTimerTitle(String externalTimerTitle) {
        this.externalTimerTitle = externalTimerTitle;
        return this;
    }

    public ModelTestBuilder withExternalTimer(ModelTest.ExternalTimer externalTimer) {
        this.externalTimer = externalTimer;
        return this;
    }





    public Model build() {
        ModelTest.ExternalTimerSystem externalTimerSystem = new ModelTest.ExternalTimerSystem(externalTimerTitle, externalTimer, gasBurner);
        return new ModelTest.CentralHeatingSystem(onOptionTitle, offOptionTitle, externalTimerSystem, gasBurner);
    }

}
