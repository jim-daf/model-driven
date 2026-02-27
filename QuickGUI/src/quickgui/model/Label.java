package quickgui.model;

// Just a text label, nothing fancy.
public class Label extends Widget {
    private final String text;

    public Label(String name, String text) {
        super(name);
        this.text = text;
    }

    public String getText() { return text; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
