package quickgui.dsl;

import quickgui.model.*;

// Builder for panels. Works the same as WindowBuilder for adding children,
// but has endPanel() to go back to the parent builder.
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

    // close this panel and go back to whatever we were building before
    public P endPanel() {
        addToParent.accept(panel);
        return parent;
    }
}
