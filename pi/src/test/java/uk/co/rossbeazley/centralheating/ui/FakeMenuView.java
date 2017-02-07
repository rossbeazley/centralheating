package uk.co.rossbeazley.centralheating.ui;

import java.util.Arrays;
import java.util.List;

class FakeMenuView implements MenuView{

    public List<String> optionsDisplayed;

    @Override
    public void presentOptions(String... optionStrings) {
        this.optionsDisplayed = Arrays.asList(optionStrings);
    }
}
