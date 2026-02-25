package quickgui.examples;

import quickgui.dsl.GUI;

/**
 * Example 1: A simple login form built using the QuickGUI internal DSL.
 *
 * Demonstrates: labels, text fields, buttons, checkboxes, panels.
 * Notice: only strings and numbers -- no Java-specific code.
 */
public class LoginFormExample {

    public static void main(String[] args) {

        GUI.window("Login")
           .size(350, 250)
           .label("Please sign in")
           .panel("Form")
               .label("Username:")
               .textField("username", 15)
               .label("Password:")
               .textField("password", 15)
               .checkBox("Remember me")
           .endPanel()
           .button("Login")
           .button("Cancel")
           .show();
    }
}
