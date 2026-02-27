package quickgui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The top-level window. Has a title, size, layout, and holds all the child elements.
public class Window extends GUIElement {
    private final String title;
    private int width = 400;
    private int height = 300;
    private Layout layout = Layout.VERTICAL;
    private final List<GUIElement> children = new ArrayList<>();
    // keeps track of where each child goes (null = no specific position)
    private final List<Position> positions = new ArrayList<>();

    public Window(String title) {
        super(title);
        this.title = title;
    }

    /* --- mutators --- */

    public void setSize(int w, int h) { this.width = w; this.height = h; }
    public void setLayout(Layout layout) { this.layout = layout; }
    public void addChild(GUIElement child, Position pos) {
        children.add(child);
        positions.add(pos);
    }

    /* --- getters --- */

    public String getTitle()   { return title; }
    public int    getWidth()   { return width; }
    public int    getHeight()  { return height; }
    public Layout getLayout() { return layout; }
    public List<GUIElement> getChildren() { return Collections.unmodifiableList(children); }
    public List<Position>   getPositions(){ return Collections.unmodifiableList(positions); }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
