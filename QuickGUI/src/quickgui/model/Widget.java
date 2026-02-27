package quickgui.model;

// Superclass for leaf widgets (stuff that isn't a container like Window/Panel).
public abstract class Widget extends GUIElement {
    private Position position; // where it goes if parent uses BorderLayout
    private String tooltip;    // hover text (can be null)

    protected Widget(String name) {
        super(name);
    }

    public void setPosition(Position pos) { this.position = pos; }
    public void setTooltip(String tip)    { this.tooltip = tip; }

    public Position getPosition() { return position; }
    public String   getTooltip()  { return tooltip; }
}
