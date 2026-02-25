package quickgui.examples;

import quickgui.dsl.GUI;

/**
 * Example 4: Generate standalone Java source code from a DSL program.
 *
 * Instead of .show(), we call .generateCode("ClassName") which
 * returns a compilable Java file as a String.
 */
public class CodeGenExample {

    public static void main(String[] args) {

        String code = GUI.window("Generated App")
           .size(400, 300)
           .label("Welcome!")
           .panel("Details")
               .label("Name:")
               .textField("name", 20)
               .label("Email:")
               .textField("email", 20)
           .endPanel()
           .button("Submit")
           .button("Clear")
           .generateCode("GeneratedApp");

        System.out.println("=== Generated Java Code ===");
        System.out.println(code);
        System.out.println("=== End of Generated Code ===");
    }
}
