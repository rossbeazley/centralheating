package uk.co.rossbeazley.centralheating.ui;

/**
 * Created by beazlr02 on 23/01/17.
 */
interface MenuView {
    void presentOptions(String... optionStrings);

    void selectOption(int indexFromZero);
}