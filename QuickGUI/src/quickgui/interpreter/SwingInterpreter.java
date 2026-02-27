package quickgui.interpreter;

import quickgui.model.Button;
import quickgui.model.CheckBox;
import quickgui.model.ComboBox;
import quickgui.model.GUIElement;
import quickgui.model.GUIVisitor;
import quickgui.model.Label;
import quickgui.model.Layout;
import quickgui.model.LayoutType;
import quickgui.model.Panel;
import quickgui.model.Position;
import quickgui.model.Separator;
import quickgui.model.Slider;
import quickgui.model.TextArea;
import quickgui.model.TextField;
import quickgui.model.Widget;
import quickgui.model.Window;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// This is the interpreter. It takes our model and turns it into actual Swing components.
// Uses the visitor pattern to go through each element in the tree.
public class SwingInterpreter implements GUIVisitor {

    // holds the last Swing component we built (visitor sets this)
    private JComponent currentComponent;

    // Main method - takes a Window model and creates the actual JFrame.
    // If the user didn't set a specific layout, we try to figure out a
    // good layout automatically (labels go to the top, buttons to the bottom, etc.)
    public void interpret(Window window) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(window.getTitle());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(window.getWidth(), window.getHeight());

            if (window.getLayout() == Layout.VERTICAL
                    && hasNoExplicitPositions(window.getPositions())) {
                applyAutoWindowLayout(frame, window);
            } else {
                frame.setLayout(createLayout(window.getLayout()));
                addChildren(frame.getContentPane(), window.getChildren(),
                            window.getPositions(), window.getLayout());
            }

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // --- visitor methods (one for each widget type) ---

    @Override
    public void visit(Window window) {
        // nothing here, window is handled in interpret()
    }

    @Override
    public void visit(Panel panel) {
        List<GUIElement> children = panel.getChildren();
        JPanel jp;

        if (panel.getLayout() == Layout.VERTICAL) {
            // try to pick the best layout based on what's inside the panel
            if (isFormLike(children)) {
                jp = buildFormPanel(children);
            } else if (isToolbarLike(children)) {
                jp = buildToolbarPanel(children);
            } else if (isStatusBarLike(children)) {
                jp = buildStatusBarPanel(children);
            } else {
                jp = new JPanel(createLayout(panel.getLayout()));
                addChildren(jp, children, panel.getPositions(), panel.getLayout());
            }
        } else {
            jp = new JPanel(createLayout(panel.getLayout()));
            addChildren(jp, children, panel.getPositions(), panel.getLayout());
        }

        if (panel.getBorderTitle() != null) {
            jp.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), panel.getBorderTitle()));
        }

        currentComponent = jp;
    }

    @Override
    public void visit(Label label) {
        JLabel jl = new JLabel(label.getText());
        applyTooltip(jl, label);
        currentComponent = jl;
    }

    @Override
    public void visit(Button button) {
        JButton jb = new JButton(button.getLabel());
        if (button.getAction() != null) {
            jb.addActionListener(e -> button.getAction().run());
        }
        applyTooltip(jb, button);
        currentComponent = jb;
    }

    @Override
    public void visit(TextField textField) {
        JTextField jtf = new JTextField(textField.getDefaultText(), textField.getColumns());
        applyTooltip(jtf, textField);
        currentComponent = jtf;
    }

    @Override
    public void visit(TextArea textArea) {
        JTextArea jta = new JTextArea(textArea.getDefaultText(),
                                       textArea.getRows(), textArea.getCols());
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        applyTooltip(jta, textArea);
        if (textArea.isScrollable()) {
            currentComponent = new JScrollPane(jta);
        } else {
            currentComponent = jta;
        }
    }

    @Override
    public void visit(CheckBox checkBox) {
        JCheckBox jcb = new JCheckBox(checkBox.getLabel(), checkBox.isSelected());
        applyTooltip(jcb, checkBox);
        currentComponent = jcb;
    }

    @Override
    public void visit(ComboBox comboBox) {
        JComboBox<String> jcb = new JComboBox<>(comboBox.getItems().toArray(new String[0]));
        jcb.setSelectedIndex(comboBox.getSelectedIndex());
        applyTooltip(jcb, comboBox);
        currentComponent = jcb;
    }

    @Override
    public void visit(Slider slider) {
        JSlider js = new JSlider(slider.getMin(), slider.getMax(), slider.getValue());
        if (slider.isShowTicks()) {
            js.setMajorTickSpacing(slider.getMajorTickSpacing());
            js.setPaintTicks(true);
            js.setPaintLabels(true);
        }
        applyTooltip(js, slider);
        currentComponent = js;
    }

    @Override
    public void visit(Separator separator) {
        int orientation = (separator.getOrientation() == Separator.Orientation.HORIZONTAL)
                          ? SwingConstants.HORIZONTAL : SwingConstants.VERTICAL;
        currentComponent = new JSeparator(orientation);
    }

    // --- auto-layout logic ---

    // checks if user set any positions manually
    private boolean hasNoExplicitPositions(List<Position> positions) {
        for (Position p : positions) {
            if (p != null) return false;
        }
        return true;
    }

    // Figures out the layout automatically for a window:
    // - labels at the start go to the top (NORTH)
    // - buttons at the end go to the bottom (SOUTH)
    // - everything in between goes to CENTER
    private void applyAutoWindowLayout(JFrame frame, Window window) {
        List<GUIElement> children = window.getChildren();

        // split children into header (labels), center (panels etc), footer (buttons)
        int headerEnd = 0;
        while (headerEnd < children.size()
                && children.get(headerEnd) instanceof Label) {
            headerEnd++;
        }

        int footerStart = children.size();
        while (footerStart > headerEnd
                && children.get(footerStart - 1) instanceof Button) {
            footerStart--;
        }

        List<GUIElement> header = children.subList(0, headerEnd);
        List<GUIElement> center = children.subList(headerEnd, footerStart);
        List<GUIElement> footer = children.subList(footerStart, children.size());

        frame.setLayout(new BorderLayout(5, 5));
        Container pane = frame.getContentPane();
        if (pane instanceof JComponent) {
            ((JComponent) pane).setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        // top: header labels (bold)
        if (!header.isEmpty()) {
            JPanel hp = new JPanel(new GridLayout(header.size(), 1, 0, 2));
            for (GUIElement el : header) {
                el.accept(this);
                currentComponent.setFont(
                    currentComponent.getFont().deriveFont(Font.BOLD, 14f));
                hp.add(currentComponent);
            }
            pane.add(hp, BorderLayout.NORTH);
        }

        // bottom: buttons in a centered row
        if (!footer.isEmpty()) {
            JPanel fp = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            for (GUIElement el : footer) {
                el.accept(this);
                fp.add(currentComponent);
            }
            pane.add(fp, BorderLayout.SOUTH);
        }

        // middle: everything else
        if (!center.isEmpty()) {
            buildCenterRegion(pane, center);
        }
    }

    // Builds the center area of the window.
    // If there's a TextArea, it gets the most space (goes in BorderLayout.CENTER
    // of a nested panel). Otherwise just stack everything vertically.
    private void buildCenterRegion(Container pane, List<GUIElement> center) {
        if (center.size() == 1) {
            center.get(0).accept(this);
            pane.add(currentComponent, BorderLayout.CENTER);
            return;
        }

        // look for a TextArea (it should get the most room)
        int taIndex = -1;
        for (int i = 0; i < center.size(); i++) {
            if (center.get(i) instanceof TextArea) {
                taIndex = i;
                break;
            }
        }

        if (taIndex >= 0) {
            // found a text area - give it center, put other stuff around it
            JPanel cp = new JPanel(new BorderLayout(5, 5));

            if (taIndex > 0) {
                JPanel top = new JPanel(new GridLayout(taIndex, 1, 0, 2));
                for (int i = 0; i < taIndex; i++) {
                    center.get(i).accept(this);
                    top.add(currentComponent);
                }
                cp.add(top, BorderLayout.NORTH);
            }

            center.get(taIndex).accept(this);
            cp.add(currentComponent, BorderLayout.CENTER);

            int afterCount = center.size() - taIndex - 1;
            if (afterCount > 0) {
                JPanel bottom = new JPanel(new GridLayout(afterCount, 1, 0, 2));
                for (int i = taIndex + 1; i < center.size(); i++) {
                    center.get(i).accept(this);
                    bottom.add(currentComponent);
                }
                cp.add(bottom, BorderLayout.SOUTH);
            }

            pane.add(cp, BorderLayout.CENTER);
        } else {
            // no text area, just stack them
            JPanel cp = new JPanel(new GridLayout(center.size(), 1, 0, 5));
            for (GUIElement el : center) {
                el.accept(this);
                cp.add(currentComponent);
            }
            pane.add(cp, BorderLayout.CENTER);
        }
    }

    // --- figuring out what kind of panel we're dealing with ---

    // a panel is "form-like" if it has label + input pairs (like Username: [____])
    private boolean isFormLike(List<GUIElement> children) {
        for (int i = 0; i < children.size() - 1; i++) {
            if (children.get(i) instanceof Label && isInputWidget(children.get(i + 1))) {
                return true;
            }
        }
        return false;
    }

    // checks if something is an input widget
    private boolean isInputWidget(GUIElement el) {
        return el instanceof TextField || el instanceof TextArea
            || el instanceof ComboBox  || el instanceof Slider
            || el instanceof CheckBox;
    }

    // toolbar = only buttons, separators, combos
    private boolean isToolbarLike(List<GUIElement> children) {
        if (children.isEmpty()) return false;
        for (GUIElement el : children) {
            if (!(el instanceof Button || el instanceof Separator
                    || el instanceof ComboBox)) {
                return false;
            }
        }
        return true;
    }

    // status bar = just labels
    private boolean isStatusBarLike(List<GUIElement> children) {
        if (children.isEmpty()) return false;
        for (GUIElement el : children) {
            if (!(el instanceof Label)) return false;
        }
        return true;
    }

    // --- building specific panel types ---

    // pairs up labels with their input fields into a 2-column grid
    // e.g. "Username:" | [text field]
    //      "Password:" | [text field]
    //      (empty)     | [checkbox]
    private JPanel buildFormPanel(List<GUIElement> children) {
        List<GUIElement[]> rows = new ArrayList<>();
        int i = 0;
        while (i < children.size()) {
            if (children.get(i) instanceof Label
                    && i + 1 < children.size()
                    && isInputWidget(children.get(i + 1))) {
                rows.add(new GUIElement[]{children.get(i), children.get(i + 1)});
                i += 2;
            } else if (children.get(i) instanceof Label) {
                rows.add(new GUIElement[]{children.get(i), null});
                i++;
            } else {
                rows.add(new GUIElement[]{null, children.get(i)});
                i++;
            }
        }

        JPanel jp = new JPanel(new GridLayout(rows.size(), 2, 5, 5));
        for (GUIElement[] row : rows) {
            if (row[0] != null) {
                row[0].accept(this);
                jp.add(currentComponent);
            } else {
                jp.add(new JLabel()); // empty spacer
            }
            if (row[1] != null) {
                row[1].accept(this);
                jp.add(currentComponent);
            } else {
                jp.add(new JLabel());
            }
        }
        return jp;
    }

    // toolbar-style: everything in a horizontal row
    private JPanel buildToolbarPanel(List<GUIElement> children) {
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        for (GUIElement el : children) {
            el.accept(this);
            jp.add(currentComponent);
        }
        return jp;
    }

    // status bar: labels in a row with some spacing
    private JPanel buildStatusBarPanel(List<GUIElement> children) {
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 2));
        for (GUIElement el : children) {
            el.accept(this);
            jp.add(currentComponent);
        }
        return jp;
    }

    // --- utility methods ---

    private void addChildren(Container parent, List<GUIElement> children,
                              List<Position> positions, Layout layout) {
        for (int i = 0; i < children.size(); i++) {
            GUIElement child = children.get(i);
            Position   pos   = positions.get(i);

            child.accept(this); // this sets currentComponent
            JComponent comp = currentComponent;

            if (layout.getType() == LayoutType.BORDER && pos != null) {
                parent.add(comp, toBorderConstraint(pos));
            } else {
                parent.add(comp);
            }
        }
    }

    // converts our Layout model to an actual Swing LayoutManager
    private LayoutManager createLayout(Layout spec) {
        switch (spec.getType()) {
            case FLOW:
                return new FlowLayout(FlowLayout.LEFT, spec.getHgap(), spec.getVgap());
            case GRID:
                return new GridLayout(spec.getRows(), spec.getCols(),
                                      spec.getHgap(), spec.getVgap());
            case BORDER:
                return new BorderLayout(spec.getHgap(), spec.getVgap());
            case VERTICAL:
                return new GridLayout(0, 1, spec.getHgap(), spec.getVgap());
            case HORIZONTAL:
                return new GridLayout(1, 0, spec.getHgap(), spec.getVgap());
            default:
                return new FlowLayout();
        }
    }

    // maps Position enum to BorderLayout constraint strings
    private String toBorderConstraint(Position pos) {
        switch (pos) {
            case NORTH:  return BorderLayout.NORTH;
            case SOUTH:  return BorderLayout.SOUTH;
            case EAST:   return BorderLayout.EAST;
            case WEST:   return BorderLayout.WEST;
            case CENTER: return BorderLayout.CENTER;
            default:     return BorderLayout.CENTER;
        }
    }

    // sets tooltip if the widget has one
    private void applyTooltip(JComponent comp, Widget widget) {
        if (widget.getTooltip() != null) {
            comp.setToolTipText(widget.getTooltip());
        }
    }
}
