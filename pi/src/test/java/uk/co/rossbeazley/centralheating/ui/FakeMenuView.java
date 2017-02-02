package uk.co.rossbeazley.centralheating.ui;

class FakeMenuView implements MenuView{
    public String optionDisplayed;

    @Override
    public void presentOption(String optionString) {
        this.optionDisplayed = optionString;
    }
}
