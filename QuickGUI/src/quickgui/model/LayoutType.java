package quickgui.model;

/**
 * Meta-model enum: Layout strategy for panels.
 *
 * FLOW   — components flow left-to-right, wrapping to next row.
 * GRID   — components arranged in a fixed rows × cols grid.
 * BORDER — five regions: NORTH, SOUTH, EAST, WEST, CENTER.
 * VERTICAL   — components stacked vertically (BoxLayout Y-axis).
 * HORIZONTAL — components side by side (BoxLayout X-axis).
 */
public enum LayoutType {
    FLOW,
    GRID,
    BORDER,
    VERTICAL,
    HORIZONTAL
}
