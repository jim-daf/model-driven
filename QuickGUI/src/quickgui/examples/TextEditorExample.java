package quickgui.examples;

import quickgui.dsl.GUI;

/**
 * Example 2: A text editor prototype.
 *
 * Demonstrates: text area, toolbar with buttons, separator, combo box, status labels.
 */
public class TextEditorExample {

    public static void main(String[] args) {

        GUI.window("QuickEdit")
           .size(600, 400)
           .panel("Toolbar")
               .button("New")
               .button("Open")
               .button("Save")
               .separator()
               .comboBox("font", "Monospaced", "Arial", "Times New Roman")
           .endPanel()
           .textArea("editor", 20, 60)
           .panel("Status")
               .label("Ready")
               .label("Ln 1, Col 1")
           .endPanel()
           .show();
    }
}
