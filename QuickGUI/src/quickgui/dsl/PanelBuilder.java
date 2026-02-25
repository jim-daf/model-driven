package quickgui.dsl;

import quickgui.model.*;

/**
 * Fluent builder for constructing a Panel model node.
 *
 * A PanelBuilder can add children just like a WindowBuilder.
 * It has an endPanel() method to return to the parent builder.
 *
 * @param <P> the type of the parent builder (for fluent return)
 */
public class PanelBuilder<P> extends ContainerBuilder<PanelBuilder<P>> {
    private final Panel panel;
    private final P parent;
    private final java.util.function.Consumer<GUIElement> addToParent;

    PanelBuilder(String title, P parent, java.util.function.Consumer<GUIElement> addToParent) {
        this.panel = new Panel(title);
        if (title != null && !title.isEmpty()) {
            this.panel.setBorderTitle(title);
        }
        this.parent = parent;
        this.addToParent = addToParent;
    }

    @Override
    protected PanelBuilder<P> self() { return this; }

    @Override
    protected void addChildToModel(GUIElement child) {
        panel.addChild(child, null);
    }

    /* --- end panel and return to parent --- */

    /**
     * Finalize this panel, add it to the parent container, and return
     * to the parent builder for further configuration.
     */
    public P endPanel() {
        addToParent.accept(panel);
        return parent;
    }
}
