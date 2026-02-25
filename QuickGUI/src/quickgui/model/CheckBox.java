package quickgui.model;

/**
 * Meta-model: CheckBox — boolean toggle widget.
 */
public class CheckBox extends Widget {
    private final String label;
    private boolean selected;

    public CheckBox(String name, String label) {
        super(name);
        this.label = label;
    }

    public void setSelected(boolean selected) { this.selected = selected; }

    public String  getLabel()    { return label; }
    public boolean isSelected()  { return selected; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
