package quickgui.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Meta-model: ComboBox — dropdown selection widget.
 */
public class ComboBox extends Widget {
    private final List<String> items;
    private int selectedIndex = 0;

    public ComboBox(String name, String... items) {
        super(name);
        this.items = Arrays.asList(items);
    }

    public void setSelectedIndex(int idx) { this.selectedIndex = idx; }

    public List<String> getItems()        { return Collections.unmodifiableList(items); }
    public int          getSelectedIndex() { return selectedIndex; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
