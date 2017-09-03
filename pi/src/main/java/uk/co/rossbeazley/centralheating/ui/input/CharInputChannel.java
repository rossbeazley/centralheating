package uk.co.rossbeazley.centralheating.ui.input;

public interface CharInputChannel {
    void onChar(ParseFunction parseFunction);

    public interface ParseFunction {
        void parse(char c);
    }
}
