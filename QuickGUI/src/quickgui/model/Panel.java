package quickgui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Meta-model: Panel — a container for organizing child widgets and sub-panels.
 *
 * Panels have their own LayoutSpec and can be nested to form a hierarchy.
 */
public class Panel extends GUIElement {
    private Layout layout = Layout.VERTICAL;
    private String borderTitle;  // optional titled border
    private Position position;   // position inside a parent BorderLayout

    private final List<GUIElement> children = new ArrayList<>();
    private final List<Position> positions = new ArrayList<>();

    public Panel(String name) {
        super(name);
    }

    /* --- mutators --- */

    public void setLayout(Layout layout) { this.layout = layout; }
    public void setBorderTitle(String t)     { this.borderTitle = t; }
    public void setPosition(Position pos)    { this.position = pos; }
    public void addChild(GUIElement child, Position pos) {
        children.add(child);
        positions.add(pos);
    }

    /* --- getters --- */

    public Layout     getLayout()      { return layout; }
    public String     getBorderTitle() { return borderTitle; }
    public Position   getPosition()    { return position; }
    public List<GUIElement> getChildren()  { return Collections.unmodifiableList(children); }
    public List<Position>   getPositions() { return Collections.unmodifiableList(positions); }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
