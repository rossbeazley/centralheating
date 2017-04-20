package uk.co.rossbeazley.centralheating.core;

class ModelTestBuilder {

     Model buildCentralHeatingSystemWithONANDOffOption(String on, String off, ModelTest.GasBurner gasBurner, ModelTest modelTest) {
        return new ModelTest.CentralHeatingSystem(on,off, modelTest.new ExternalTimerSystem("", new ModelTest.ExternalTimer(null), gasBurner), gasBurner);
    }

     Model buildCentralHeatingSystemWithONANDOffOptionAndExternalTimerSupport(String on, String off, String external, ModelTest.GasBurner gasBurner, ModelTest.ExternalTimer externalTimer, ModelTest modelTest) {
        ModelTest.ExternalTimerSystem externalTimerSystem = modelTest.new ExternalTimerSystem(external, externalTimer, gasBurner);
        return new ModelTest.CentralHeatingSystem(on,off,externalTimerSystem, gasBurner);
    }

     Model buildCentralHeatingSystemWithONOption(String onOptionTitle, ModelTest.GasBurner gasBurner, ModelTest modelTest) {
        return new ModelTest.CentralHeatingSystem(onOptionTitle, null, modelTest.new ExternalTimerSystem("", new ModelTest.ExternalTimer(null), gasBurner),gasBurner);
    }
}
