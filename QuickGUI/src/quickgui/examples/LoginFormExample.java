package quickgui.examples;

import quickgui.dsl.GUI;

// Login form example - shows labels, text fields, buttons, checkbox, and a panel.
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
           .button("Login",() -> System.out.println("Login button pressed!"))
           .button("Cancel",() -> System.out.println("Cancel button pressed!"))
           .show();
    }
}
