package quickgui.model;

/**
 * Meta-model: Slider — range selection widget.
 */
public class Slider extends Widget {
    private int min = 0;
    private int max = 100;
    private int value = 50;
    private boolean showTicks = false;
    private int majorTickSpacing = 10;

    public Slider(String name) {
        super(name);
    }

    public void setMin(int min)                   { this.min = min; }
    public void setMax(int max)                   { this.max = max; }
    public void setValue(int value)               { this.value = value; }
    public void setShowTicks(boolean show)        { this.showTicks = show; }
    public void setMajorTickSpacing(int spacing)  { this.majorTickSpacing = spacing; }

    public int     getMin()              { return min; }
    public int     getMax()              { return max; }
    public int     getValue()            { return value; }
    public boolean isShowTicks()         { return showTicks; }
    public int     getMajorTickSpacing() { return majorTickSpacing; }

    @Override
    public void accept(GUIVisitor visitor) { visitor.visit(this); }
}
