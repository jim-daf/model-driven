package quickgui.model;

// The different kinds of layout we support.
// FLOW = left-to-right wrapping, GRID = rows x cols,
// BORDER = 5 regions (N/S/E/W/Center), VERTICAL/HORIZONTAL = stacking.
public enum LayoutType {
    FLOW,
    GRID,
    BORDER,
    VERTICAL,
    HORIZONTAL
}
