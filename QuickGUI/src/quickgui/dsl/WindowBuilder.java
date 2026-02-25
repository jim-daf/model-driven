package quickgui.dsl;

import quickgui.model.*;
import quickgui.interpreter.SwingInterpreter;
import quickgui.codegen.JavaCodeGenerator;

/**
 * Fluent builder for constructing a Window model node.
 *
 * Supports chaining: size, adding widgets, adding panels.
 * Terminal operations: build(), show(), generateCode().
 */
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

    /* --- window-specific configuration --- */

    /** Set the window size in pixels. */
    public WindowBuilder size(int width, int height) {
        window.setSize(width, height);
        return this;
    }

    /* --- terminal operations --- */

    /** Build and return the Window model object. */
    public Window build() {
        return window;
    }

    /** Build the model and immediately display it using the Swing interpreter. */
    public Window show() {
        Window w = build();
        new SwingInterpreter().interpret(w);
        return w;
    }

    /**
     * Build the model and generate a standalone Java source file.
     *
     * @param className the name of the generated class
     * @return the generated Java source code as a String
     */
    public String generateCode(String className) {
        Window w = build();
        JavaCodeGenerator gen = new JavaCodeGenerator(className);
        return gen.generate(w);
    }
}
