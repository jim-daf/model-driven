package quickgui.model;

/**
 * Visitor interface for traversing the GUI meta-model.
 * Used by the SwingInterpreter and the JavaCodeGenerator.
 */
public interface GUIVisitor {
    void visit(Window window);
    void visit(Panel panel);
    void visit(Label label);
    void visit(Button button);
    void visit(TextField textField);
    void visit(TextArea textArea);
    void visit(CheckBox checkBox);
    void visit(ComboBox comboBox);
    void visit(Slider slider);
    void visit(Separator separator);
}
