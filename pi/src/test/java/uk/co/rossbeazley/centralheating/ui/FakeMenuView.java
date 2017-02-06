package uk.co.rossbeazley.centralheating.ui;

import java.util.List;

class FakeMenuView implements MenuView{
    public String optionDisplayed;
    public List<String> optionsDisplayed;

    @Override
    public void presentOption(String optionString) {
        this.optionDisplayed = optionString;
    }
}
