package quickgui.model;

/**
 * Meta-model: Abstract superclass for all leaf widgets (non-containers).
 */
public abstract class Widget extends GUIElement {
    private Position position; // optional BorderLayout position
    private String tooltip;

    protected Widget(String name) {
        super(name);
    }

    public void setPosition(Position pos) { this.position = pos; }
    public void setTooltip(String tip)    { this.tooltip = tip; }

    public Position getPosition() { return position; }
    public String   getTooltip()  { return tooltip; }
}
