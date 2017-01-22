package uk.co.rossbeazley.centralheating.ui;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OpeningTheMenu2 {

    @Test
    public void t() {
        class ExpectedSDLView implements SDLView{}
        SDLView expectedScreen = new ExpectedSDLView();


        class ViewFactory {
            public SDLView instantiateView(Class<? extends SDLView> clazz) {
                return expectedScreen;
            }
        }


        SUT sut = new SUT();

        sut.buttonPresS();

        String presentedScreen = null;
        assertThat(presentedScreen,is(expectedScreen));
    }

    private interface SDLView {
    }

    private class SUT {
        public void buttonPresS() {

        }
    }
}
