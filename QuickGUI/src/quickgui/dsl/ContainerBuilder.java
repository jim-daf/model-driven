package quickgui.dsl;

import quickgui.model.*;

// Shared base class for WindowBuilder and PanelBuilder.
// Has all the methods for adding widgets (label, button, textField, etc.).
// Uses generics so that each method returns the right builder type (CRTP pattern).
public abstract class ContainerBuilder<SELF extends ContainerBuilder<SELF>> {

    // subclasses must implement these two
    protected abstract SELF self();
    protected abstract void addChildToModel(GUIElement child);

    // --- Label ---

    public SELF label(String text) {
        Label lbl = new Label(text, text);
        addChildToModel(lbl);
        return self();
    }

    // --- Button ---

    public SELF button(String text) {
        Button btn = new Button(text, text);
        addChildToModel(btn);
        return self();
    }

    // button with a click handler
    public SELF button(String text, Runnable action) {
        Button btn = new Button(text, text);
        btn.setAction(action);
        addChildToModel(btn);
        return self();
    }

    // --- TextField ---

    public SELF textField(String name) {
        TextField tf = new TextField(name);
        addChildToModel(tf);
        return self();
    }

    // text field with column width
    public SELF textField(String name, int columns) {
        TextField tf = new TextField(name);
        tf.setColumns(columns);
        addChildToModel(tf);
        return self();
    }

    // --- TextArea ---

    public SELF textArea(String name) {
        TextArea ta = new TextArea(name);
        addChildToModel(ta);
        return self();
    }

    // text area with specific size
    public SELF textArea(String name, int rows, int cols) {
        TextArea ta = new TextArea(name);
        ta.setRows(rows);
        ta.setCols(cols);
        addChildToModel(ta);
        return self();
    }

    // --- CheckBox ---

    public SELF checkBox(String text) {
        CheckBox cb = new CheckBox(text, text);
        addChildToModel(cb);
        return self();
    }

    // --- ComboBox ---

    public SELF comboBox(String name, String... items) {
        ComboBox cb = new ComboBox(name, items);
        addChildToModel(cb);
        return self();
    }

    // --- Slider ---

    public SELF slider(String name, int min, int max, int value) {
        Slider s = new Slider(name);
        s.setMin(min);
        s.setMax(max);
        s.setValue(value);
        addChildToModel(s);
        return self();
    }

    // --- Separator ---

    public SELF separator() {
        Separator sep = new Separator("sep", Separator.Orientation.HORIZONTAL);
        addChildToModel(sep);
        return self();
    }

    // --- Panel (nested container) ---

    // opens a sub-panel, call .endPanel() when done adding stuff to it
    public PanelBuilder<SELF> panel(String title) {
        return new PanelBuilder<>(title, self(), this::addChildToModel);
    }
}
