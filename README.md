# QuickGUI — Internal DSL for Quick GUI Prototyping

A **model-driven** internal DSL in Java for rapidly prototyping graphical user
interfaces. Create your GUI using the DSL and **show it instantly** (interpreter)

---

## Quick Start

### Compile

```bash
cd QuickGUI
javac -d out src/quickgui/model/*.java src/quickgui/dsl/*.java \
      src/quickgui/interpreter/*.java src/quickgui/codegen/*.java \
      src/quickgui/examples/*.java
```

### Run an Example

```bash
# Show the login form GUI
java -cp out quickgui.examples.LoginFormExample

# Show the text editor GUI
java -cp out quickgui.examples.TextEditorExample

# Show the settings form GUI
java -cp out quickgui.examples.SettingsFormExample

# Generate standalone Java code (prints to stdout)
java -cp out quickgui.examples.CodeGenExample
```

---

## DSL Usage

Every DSL method takes **only strings and numbers** — no Java-specific types needed.

### Minimal Example

```java
import quickgui.dsl.GUI;

public class HelloGUI {
    public static void main(String[] args) {
        GUI.window("Hello")
           .size(300, 100)
           .label("Hello, World!")
           .show();
    }
}
```

### Login Form (full example)

```java
import quickgui.dsl.GUI;

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
```

### Code Generation

```java
String code = GUI.window("My App")
    .label("Hello")
    .button("OK")
    .generateCode("MyApp");

System.out.println(code);  // prints compilable Java Swing code
```

---


### Internal DSL Techniques

1. **Method chaining**: Every builder method returns `this` (or `self()`) for fluent chains.
2. **Nested builders**: `.panel("title") ... .endPanel()` creates a scope that returns
   to the parent builder with the correct type.
3. **Generics (CRTP)**: `ContainerBuilder<SELF>` ensures that methods inherited from the
   base class return the concrete builder type, maintaining fluency.

---

## Available Widgets

| DSL Method                                  | Widget     | Description                |
|---------------------------------------------|------------|----------------------------|
| `.label("text")`                            | Label      | Static text                |
| `.button("text")`                           | Button     | Clickable button           |
| `.textField("name", cols)`                  | TextField  | Single-line text input     |
| `.textArea("name", rows, cols)`             | TextArea   | Multi-line text input      |
| `.checkBox("text")`                         | CheckBox   | Boolean toggle             |
| `.comboBox("name", "a", "b", ...)`          | ComboBox   | Dropdown selector          |
| `.slider("name", min, max, value)`          | Slider     | Range selector             |
| `.separator()`                              | Separator  | Horizontal divider         |
| `.panel("title") ... .endPanel()`           | Panel      | Titled group of children   |
