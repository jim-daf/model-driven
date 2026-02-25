package quickgui.dsl;

import quickgui.model.*;

/**
 * Abstract base for builders that can contain child elements (Window and Panel).
 *
 * Provides the fluent API methods for adding widgets and sub-panels.
 * Every method accepts only Strings and/or numbers -- no Java-specific types.
 *
 * @param <SELF> the concrete builder type (for CRTP fluent return)
 */
public abstract class ContainerBuilder<SELF extends ContainerBuilder<SELF>> {

    protected abstract SELF self();

    /** Add a child element to the underlying model. */
    protected abstract void addChildToModel(GUIElement child);

    // ===== Label =====

    /** Add a static text label. */
    public SELF label(String text) {
        Label lbl = new Label(text, text);
        addChildToModel(lbl);
        return self();
    }

    // ===== Button =====

    /** Add a clickable button. */
    public SELF button(String text) {
        Button btn = new Button(text, text);
        addChildToModel(btn);
        return self();
    }

    // ===== TextField =====

    /** Add a single-line text input. */
    public SELF textField(String name) {
        TextField tf = new TextField(name);
        addChildToModel(tf);
        return self();
    }

    /** Add a single-line text input with a specific column width. */
    public SELF textField(String name, int columns) {
        TextField tf = new TextField(name);
        tf.setColumns(columns);
        addChildToModel(tf);
        return self();
    }

    // ===== TextArea =====

    /** Add a multi-line text area. */
    public SELF textArea(String name) {
        TextArea ta = new TextArea(name);
        addChildToModel(ta);
        return self();
    }

    /** Add a multi-line text area with specific dimensions. */
    public SELF textArea(String name, int rows, int cols) {
        TextArea ta = new TextArea(name);
        ta.setRows(rows);
        ta.setCols(cols);
        addChildToModel(ta);
        return self();
    }

    // ===== CheckBox =====

    /** Add a boolean toggle checkbox. */
    public SELF checkBox(String text) {
        CheckBox cb = new CheckBox(text, text);
        addChildToModel(cb);
        return self();
    }

    // ===== ComboBox =====

    /** Add a dropdown selector with the given items. */
    public SELF comboBox(String name, String... items) {
        ComboBox cb = new ComboBox(name, items);
        addChildToModel(cb);
        return self();
    }

    // ===== Slider =====

    /** Add a range slider. */
    public SELF slider(String name, int min, int max, int value) {
        Slider s = new Slider(name);
        s.setMin(min);
        s.setMax(max);
        s.setValue(value);
        addChildToModel(s);
        return self();
    }

    // ===== Separator =====

    /** Add a horizontal divider line. */
    public SELF separator() {
        Separator sep = new Separator("sep", Separator.Orientation.HORIZONTAL);
        addChildToModel(sep);
        return self();
    }

    // ===== Panel (nested container) =====

    /** Start a sub-panel with the given title. Call .endPanel() to close it. */
    public PanelBuilder<SELF> panel(String title) {
        return new PanelBuilder<>(title, self(), this::addChildToModel);
    }
}
