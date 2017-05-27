package uk.co.rossbeazley.centralheating.lanternaSpike;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;

public class MenuFirmingTest {

    @Test
    public void
    createView() throws Exception {
        CapturingBasePane window = new CapturingBasePane();
        Menu.createView(window);

        assertThat(window.component,is(instanceOf(Panel.class)));

        Panel p = (Panel) window.component;

        assertThat(p.getChildCount(),is(1));
        assertThat(p.getChildren().toArray()[0],is(instanceOf(ActionListBox.class)));
        ActionListBox actual = (ActionListBox) p.getChildren().toArray()[0];
        assertThat(actual.getItemCount(),is(4));
        assertThat(actual.getItemAt(0).toString(),is("On"));
        assertThat(actual.getItemAt(1).toString(),is("Off"));
        assertThat(actual.getItemAt(2).toString(),is("External"));
        assertThat(actual.getItemAt(3).toString(),is("Boost"));

    }

    private static class CapturingBasePane implements BasePane {
        private Component component;

        @Override
        public TextGUI getTextGUI() {
            return null;
        }

        @Override
        public void draw(TextGUIGraphics graphics) {

        }

        @Override
        public boolean isInvalid() {
            return false;
        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean handleInput(KeyStroke key) {
            return false;
        }

        @Override
        public Component getComponent() {
            return null;
        }

        @Override
        public void setComponent(Component component) {

            this.component = component;
        }

        @Override
        public Interactable getFocusedInteractable() {
            return null;
        }

        @Override
        public void setFocusedInteractable(Interactable interactable) {

        }

        @Override
        public TerminalPosition getCursorPosition() {
            return null;
        }

        @Override
        public TerminalPosition toGlobal(TerminalPosition localPosition) {
            return null;
        }

        @Override
        public TerminalPosition fromGlobal(TerminalPosition position) {
            return null;
        }

        @Override
        public void setStrictFocusChange(boolean strictFocusChange) {

        }

        @Override
        public void setEnableDirectionBasedMovements(boolean enableDirectionBasedMovements) {

        }

        @Override
        public Theme getTheme() {
            return null;
        }

        @Override
        public void setTheme(Theme theme) {

        }
    }
}