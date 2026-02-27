package quickgui.model;

// Base class for everything in our GUI model.
// Each element gets a name so we can refer to it later.
public abstract class GUIElement {
    private final String name;

    protected GUIElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // visitor pattern hook
    public abstract void accept(GUIVisitor visitor);
}
