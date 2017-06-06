package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
public interface ViewFramework {
    <V> V create(Class<V> view);

}
