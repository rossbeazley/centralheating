package uk.co.rossbeazley.centralheating.core;

import java.util.List;

public interface Model {
    List<Option> options();

    void configure(Option option, Callback callback);

    public static interface Callback {
        void OK();

        void NOTOK();
    }
}
