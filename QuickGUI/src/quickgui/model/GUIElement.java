package quickgui.model;

/**
 * Root of the meta-model hierarchy.
 * Every element in a GUI has a name (identifier) used for referencing.
 *
 * Meta-model concept: GUIElement is the abstract supertype for all model nodes.
 */
public abstract class GUIElement {
    private final String name;

    protected GUIElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /** Accept a visitor (used by interpreter and code generator). */
    public abstract void accept(GUIVisitor visitor);
}
