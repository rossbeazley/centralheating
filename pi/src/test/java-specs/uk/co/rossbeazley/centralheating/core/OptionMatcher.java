package uk.co.rossbeazley.centralheating.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class OptionMatcher {
    static <T> Matcher<? super Option> withTitle(final String title) {
        return new BaseMatcher<Option>() {
            @Override
            public boolean matches(Object item) {
                return title.equals(((Option) item).name());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Option with title ").appendValue(title);
            }
        };
    }
}
