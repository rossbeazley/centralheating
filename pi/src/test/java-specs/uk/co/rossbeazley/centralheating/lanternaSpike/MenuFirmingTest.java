package uk.co.rossbeazley.centralheating.lanternaSpike;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MenuFirmingTest {

    @Test
    public void
    createView() throws Exception {
        CapturingLanternaComposite window = new CapturingLanternaComposite();
        Menu.createView(window, "Menu 1");

        assertThat(window.component, is(instanceOf(Panel.class)));

        Panel p = (Panel) window.component;

        assertThat(p.getChildCount(), is(2));
        assertThat(p.getChildren().toArray()[1], is(instanceOf(ActionListBox.class)));
        ActionListBox actual = (ActionListBox) p.getChildren().toArray()[1];
        assertThat(actual.getItemCount(), is(4));
        assertThat(actual.getItemAt(0).toString(), is("On"));
        assertThat(actual.getItemAt(1).toString(), is("Off"));
        assertThat(actual.getItemAt(2).toString(), is("External"));
        assertThat(actual.getItemAt(3).toString(), is("Boost"));

    }

    @Test
    public void
    findByHashCode() {
        CapturingLanternaComposite window = new CapturingLanternaComposite();
        Menu.createView(window, "Menu 1");

        int hashCode = 0x26;
        Component component = findComponentInCompositeByHashCode(window, hashCode);

        assertThat(component, instanceOf(ActionListBox.class));
    }

    static public Component findComponentInCompositeByHashCode(Composite window, int hashCode) {

        if (window.getComponent().hashCode() == hashCode) {
            return window.getComponent();
        }

        if (window.getComponent() instanceof Container) {
            Container c = (Container) window.getComponent();
            Component result = findComponentInContainerByHashCode(hashCode, c);
            if (result != null) return result;
        }

        return new NO_COMPONENT_FOUND(hashCode);
    }

    private static Component findComponentInContainerByHashCode(int hashCode, Container c) {
        for (Component component : c.getChildren()) {
            if (component.hashCode() == hashCode) {
                return component;
            } //else findComponentInComponentByHashCode
        }
        return null;
    }

    public static class CapturingLanternaComposite implements Composite {
        private Component component;

        @Override
        public Component getComponent() {
            return component;
        }

        @Override
        public void setComponent(Component component) {
            this.component = component;
        }

    }

    public static class NO_COMPONENT_FOUND implements Component {
        private final String hashCodeString;

        public NO_COMPONENT_FOUND(int hashCode) {
            this.hashCodeString = Integer.toHexString(hashCode);
        }

        @Override
        public TerminalPosition getPosition() {
            return null;
        }

        @Override
        public Component setPosition(TerminalPosition position) {
            return null;
        }

        @Override
        public TerminalSize getSize() {
            return null;
        }

        @Override
        public Component setSize(TerminalSize size) {
            return null;
        }

        @Override
        public TerminalSize getPreferredSize() {
            return null;
        }

        @Override
        public Component setPreferredSize(TerminalSize explicitPreferredSize) {
            return null;
        }

        @Override
        public Component setLayoutData(LayoutData data) {
            return null;
        }

        @Override
        public LayoutData getLayoutData() {
            return null;
        }

        @Override
        public Container getParent() {
            return null;
        }

        @Override
        public boolean hasParent(Container parent) {
            return false;
        }

        @Override
        public TextGUI getTextGUI() {
            return null;
        }

        @Override
        public Theme getTheme() {
            return null;
        }

        @Override
        public ThemeDefinition getThemeDefinition() {
            return null;
        }

        @Override
        public Component setTheme(Theme theme) {
            return null;
        }

        @Override
        public boolean isInside(Container container) {
            return false;
        }

        @Override
        public ComponentRenderer<? extends Component> getRenderer() {
            return null;
        }

        @Override
        public void invalidate() {

        }

        @Override
        public Border withBorder(Border border) {
            return null;
        }

        @Override
        public TerminalPosition toBasePane(TerminalPosition position) {
            return null;
        }

        @Override
        public TerminalPosition toGlobal(TerminalPosition position) {
            return null;
        }

        @Override
        public BasePane getBasePane() {
            return null;
        }

        @Override
        public Component addTo(Panel panel) {
            return null;
        }

        @Override
        public void onAdded(Container container) {

        }

        @Override
        public void onRemoved(Container container) {

        }

        @Override
        public void draw(TextGUIGraphics graphics) {

        }

        @Override
        public boolean isInvalid() {
            return false;
        }

        @Override
        public String toString() {
            return "NO_COMPONENT_FOUND::0x" + hashCodeString;
        }
    }
}