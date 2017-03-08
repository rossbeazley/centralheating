package uk.co.rossbeazley.centralheating.ui;

import java.util.Arrays;
import java.util.List;

class FakeMenuView implements MenuView{

    public List<String> optionsDisplayed;
    public String optionSelected;

    @Override
    public void presentOptions(String... optionStrings) {
        this.optionsDisplayed = Arrays.asList(optionStrings);
    }

    @Override public void selectOption(int indexFromZero) {
        optionSelected = optionsDisplayed.get(indexFromZero);
    }
}
