package quickgui.model;

// Just a line to visually separate things.
public class Separator extends Widget {
    public enum Orientation { HORIZONTAL, VERTICAL }

    private final Orientation orientation;

    public Separator(String name, Orientation orientation) {
        super(name);
        this.orientation = orientation;
    }

    public Orientation getOrientation() { return orientation; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
