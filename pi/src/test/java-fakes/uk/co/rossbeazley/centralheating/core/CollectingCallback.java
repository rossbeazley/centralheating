package uk.co.rossbeazley.centralheating.core;

class CollectingCallback implements Model.Callback {

    public static final Object SET = "set",
            UNSET = "unset";

    public Object ok = UNSET;

    @Override
    public void OK() {
        ok = SET;
    }

    @Override
    public void RANGE(HeatingTimeRange heatingTimeRange) {

    }
}
