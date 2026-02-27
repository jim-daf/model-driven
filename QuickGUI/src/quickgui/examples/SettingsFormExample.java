package quickgui.examples;

import quickgui.dsl.GUI;

// Settings dialog with combo boxes, sliders, checkboxes, etc.
public class SettingsFormExample {

    public static void main(String[] args) {

        GUI.window("Settings")
           .size(400, 350)
           .panel("Appearance")
               .label("Theme:")
               .comboBox("theme", "Light", "Dark", "System Default")
               .label("Font Size:")
               .slider("fontSize", 8, 32, 14)
               .checkBox("Show line numbers")
               .checkBox("Word wrap")
           .endPanel()
           .panel("Edit Profile")
               .label("Change username:")
               .textField("username", 20)
               .label("Change password:")
               .textField("password", 6)
           .endPanel()
           .show();
    }
}
