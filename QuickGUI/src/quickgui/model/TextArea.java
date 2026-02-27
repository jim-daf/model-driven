package quickgui.model;

// Multi-line text input (like a notepad area).
public class TextArea extends Widget {
    private int rows = 5;
    private int cols = 30;
    private String defaultText = "";
    private boolean scrollable = true;

    public TextArea(String name) {
        super(name);
    }

    public void setRows(int rows)           { this.rows = rows; }
    public void setCols(int cols)           { this.cols = cols; }
    public void setDefaultText(String text) { this.defaultText = text; }
    public void setScrollable(boolean s)    { this.scrollable = s; }

    public int     getRows()        { return rows; }
    public int     getCols()        { return cols; }
    public String  getDefaultText() { return defaultText; }
    public boolean isScrollable()   { return scrollable; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
