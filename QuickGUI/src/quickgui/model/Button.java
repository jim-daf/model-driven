package quickgui.model;

// A button the user can click. Has a label and an optional action (Runnable).
public class Button extends Widget {
    private final String label;
    private Runnable action;

    public Button(String name, String label) {
        super(name);
        this.label = label;
    }

    public void setAction(Runnable action) { this.action = action; }

    public String   getLabel()  { return label; }
    public Runnable getAction() { return action; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
