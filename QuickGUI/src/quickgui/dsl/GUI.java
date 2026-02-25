package quickgui.dsl;

import quickgui.model.*;

/**
 * Entry point of the internal DSL.
 *
 * Usage:
 *   GUI.window("Title")
 *      .size(400, 300)
 *      .label("Hello!")
 *      .button("OK")
 *      .show();
 */
public final class GUI {

    private GUI() {} // utility class

    /**
     * Start building a new window with the given title.
     *
     * @param title the window title
     * @return a WindowBuilder for further configuration
     */
    public static WindowBuilder window(String title) {
        return new WindowBuilder(title);
    }
}
