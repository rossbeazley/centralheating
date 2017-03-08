package uk.co.rossbeazley.centralheating.core;

public interface ConfigureAction {
    void configure(Option option, Model.Callback callback, FakeModel fakeModel);
}
