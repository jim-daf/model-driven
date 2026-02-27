package quickgui.dsl;

import quickgui.model.*;

// Main entry point for the DSL.
// Usage: GUI.window("Title").size(400,300).label("Hi").button("OK").show();
public final class GUI {

    private GUI() {} // can't instantiate this

    // starts building a new window
    public static WindowBuilder window(String title) {
        return new WindowBuilder(title);
    }
}
