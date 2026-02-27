package quickgui.examples;

import quickgui.dsl.GUI;

// Simple text editor - toolbar buttons, a big text area, and a status bar at the bottom.
public class TextEditorExample {

    public static void main(String[] args) {

        GUI.window("QuickEdit")
           .size(600, 400)
           .panel("Toolbar")
               .button("New")
               .button("Open")
               .button("Save")
               .separator()
               .comboBox("font", "Calibri", "Arial", "Times New Roman")
           .endPanel()
           .textArea("editor", 20, 60)
           .panel("Status")
               .label("Ready")
               .label("Ln 1, Col 1")
           .endPanel()
           .show();
    }
}
