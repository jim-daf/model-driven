package quickgui.model;

/**
 * Meta-model: TextField — single-line text input.
 */
public class TextField extends Widget {
    private int columns = 20;
    private String defaultText = "";

    public TextField(String name) {
        super(name);
    }

    public void setColumns(int cols)        { this.columns = cols; }
    public void setDefaultText(String text) { this.defaultText = text; }

    public int    getColumns()     { return columns; }
    public String getDefaultText() { return defaultText; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
