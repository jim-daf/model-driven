package quickgui.model;

/**
 * High-level layout specification for containers (Window, Panel).
 *
 * Designed so that DSL users never need to think about Swing layout managers.
 * Instead they pick a readable constant or factory and optionally set spacing:
 *
 *   Layout.VERTICAL              - stack children top-to-bottom
 *   Layout.HORIZONTAL            - place children side by side
 *   Layout.FLOW                  - wrap children left-to-right
 *   Layout.BORDER                - five regions (NORTH, SOUTH, EAST, WEST, CENTER)
 *   Layout.grid(rows, cols)      - fixed grid
 *   Layout.columns(n)            - single-row grid with n columns
 *
 * Spacing:
 *   Layout.HORIZONTAL.withGap(10)        - uniform gap
 *   Layout.grid(3, 2).withGap(5, 10)     - horizontal & vertical gap
 */
public class Layout {

    /* -- internal state (kept package-visible for interpreter/codegen) -- */

    private final LayoutType type;
    private int rows;
    private int cols;
    private int hgap;
    private int vgap;

    private Layout(LayoutType type) {
        this.type = type;
    }

    /* ---------------------------------------------------------
     *  Pre-built constants -- the simplest way to pick a layout
     * --------------------------------------------------------- */

    /** Stack children top-to-bottom. */
    public static final Layout VERTICAL   = new Layout(LayoutType.VERTICAL);

    /** Place children side by side, left-to-right. */
    public static final Layout HORIZONTAL = new Layout(LayoutType.HORIZONTAL);

    /** Children flow left-to-right and wrap to the next row when full. */
    public static final Layout FLOW       = new Layout(LayoutType.FLOW);

    /** Five named regions: NORTH, SOUTH, EAST, WEST, CENTER. */
    public static final Layout BORDER     = new Layout(LayoutType.BORDER);

    /* ---------------------------------------------------------
     *  Factory methods -- for layouts that need parameters
     * --------------------------------------------------------- */

    /** Fixed grid with the given number of rows and columns. */
    public static Layout grid(int rows, int cols) {
        Layout l = new Layout(LayoutType.GRID);
        l.rows = rows;
        l.cols = cols;
        return l;
    }

    /** Convenience: single-row grid with {@code n} columns. */
    public static Layout columns(int n) {
        return grid(0, n);
    }

    /* ---------------------------------------------------------
     *  Spacing modifier -- chain after a constant or factory
     *
     *  Layout.BORDER.withGap(5)
     *  Layout.grid(3, 2).withGap(5, 10)
     * --------------------------------------------------------- */

    /** Set uniform spacing between elements. */
    public Layout withGap(int gap) {
        return withGap(gap, gap);
    }

    /** Set horizontal and vertical spacing separately. */
    public Layout withGap(int hgap, int vgap) {
        Layout copy = new Layout(this.type);
        copy.rows  = this.rows;
        copy.cols  = this.cols;
        copy.hgap  = hgap;
        copy.vgap  = vgap;
        return copy;
    }

    /* ---------------------------------------------------------
     *  Getters (used by SwingInterpreter & JavaCodeGenerator)
     * --------------------------------------------------------- */

    public LayoutType getType() { return type; }
    public int getRows()  { return rows; }
    public int getCols()  { return cols; }
    public int getHgap()  { return hgap; }
    public int getVgap()  { return vgap; }
}
