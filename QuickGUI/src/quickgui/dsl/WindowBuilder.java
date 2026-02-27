package quickgui.dsl;

import quickgui.model.*;
import quickgui.interpreter.SwingInterpreter;

// Builder for the Window. Lets you chain .size(), .label(), .button(), etc.
// Call .show() at the end to display it.
public class WindowBuilder extends ContainerBuilder<WindowBuilder> {
    private final Window window;

    WindowBuilder(String title) {
        this.window = new Window(title);
    }

    @Override
    protected WindowBuilder self() { return this; }

    @Override
    protected void addChildToModel(GUIElement child) {
        window.addChild(child, null);
    }

    // window-specific stuff

    // set window dimensions in pixels
    public WindowBuilder size(int width, int height) {
        window.setSize(width, height);
        return this;
    }

    // terminal operations (these end the chain)

    // just returns the model object without showing anything
    public Window build() {
        return window;
    }

    // builds the model and pops up the window using Swing
    public Window show() {
        Window w = build();
        new SwingInterpreter().interpret(w);
        return w;
    }
}
