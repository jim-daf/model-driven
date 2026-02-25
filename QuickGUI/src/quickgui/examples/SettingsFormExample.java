package quickgui.examples;

import quickgui.dsl.GUI;

/**
 * Example 3: A settings dialog prototype.
 *
 * Demonstrates: grouped panels, combo boxes, sliders, checkboxes, text fields.
 */
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
           .panel("Network")
               .label("Proxy Host:")
               .textField("proxyHost", 20)
               .label("Proxy Port:")
               .textField("proxyPort", 6)
           .endPanel()
           .show();
    }
}
