package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
public interface ViewFramework {
    <V,T extends V> T create(Class<V> view);

}
