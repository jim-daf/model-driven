package quickgui.model;

// Represents a layout choice for containers. The user can pick from
// some predefined ones (VERTICAL, FLOW, etc.) or create a grid.
// Also supports setting gaps between elements.
//
// Examples:
//   Layout.VERTICAL           - stack stuff top to bottom
//   Layout.grid(3, 2)         - 3 rows, 2 columns
//   Layout.FLOW.withGap(10)   - flow layout with 10px gaps
public class Layout {

    // internal fields

    private final LayoutType type;
    private int rows;
    private int cols;
    private int hgap;
    private int vgap;

    private Layout(LayoutType type) {
        this.type = type;
    }

    // ready-made layout constants

    public static final Layout VERTICAL   = new Layout(LayoutType.VERTICAL);
    public static final Layout HORIZONTAL = new Layout(LayoutType.HORIZONTAL);
    public static final Layout FLOW       = new Layout(LayoutType.FLOW);
    public static final Layout BORDER     = new Layout(LayoutType.BORDER);

    // factory methods for layouts that need extra params

    // makes a grid with given rows and cols
    public static Layout grid(int rows, int cols) {
        Layout l = new Layout(LayoutType.GRID);
        l.rows = rows;
        l.cols = cols;
        return l;
    }

    // shortcut: 1 row with n columns
    public static Layout columns(int n) {
        return grid(0, n);
    }

    // gap modifier - can chain it after any layout like Layout.BORDER.withGap(5)

    public Layout withGap(int gap) {
        return withGap(gap, gap);
    }

    // separate h and v gaps
    public Layout withGap(int hgap, int vgap) {
        Layout copy = new Layout(this.type);
        copy.rows  = this.rows;
        copy.cols  = this.cols;
        copy.hgap  = hgap;
        copy.vgap  = vgap;
        return copy;
    }

    // getters (used by the interpreter and code generator)

    public LayoutType getType() { return type; }
    public int getRows()  { return rows; }
    public int getCols()  { return cols; }
    public int getHgap()  { return hgap; }
    public int getVgap()  { return vgap; }
}
