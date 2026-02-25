# QuickGUI — Internal DSL for Quick GUI Prototyping

A **model-driven** internal DSL in Java for rapidly prototyping graphical user
interfaces.  Instead of writing 50+ lines of Swing boilerplate, describe your
GUI in a few lines of fluent Java and either **show it instantly** (interpreter)
or **generate standalone Java code** (code generator).

---

## Project Structure

```
QuickGUI/
├── docs/
│   └── BNF_GRAMMAR.md          # Formal BNF grammar of the DSL
├── src/
│   └── quickgui/
│       ├── model/               # Meta-model (domain classes)
│       │   ├── GUIElement.java      Abstract root of all elements
│       │   ├── GUIVisitor.java      Visitor interface
│       │   ├── Window.java          Top-level container
│       │   ├── Panel.java           Nested container
│       │   ├── Widget.java          Abstract leaf widget
│       │   ├── Label.java           Static text
│       │   ├── Button.java          Clickable button
│       │   ├── TextField.java       Single-line input
│       │   ├── TextArea.java        Multi-line input
│       │   ├── CheckBox.java        Boolean toggle
│       │   ├── ComboBox.java        Dropdown selector
│       │   ├── Slider.java          Range selector
│       │   ├── Separator.java       Visual divider
│       │   ├── Layout.java          High-level layout configuration
│       │   ├── LayoutType.java      Layout type enum
│       │   └── Position.java        BorderLayout position enum
│       ├── dsl/                 # Internal DSL (fluent builders)
│       │   ├── GUI.java             Entry point: GUI.window(...)
│       │   ├── WindowBuilder.java   Builds Window model
│       │   ├── PanelBuilder.java    Builds Panel model
│       │   └── ContainerBuilder.java Shared widget-adding API
│       ├── interpreter/         # Semantics: Swing interpreter
│       │   └── SwingInterpreter.java
│       ├── codegen/             # Semantics: Java code generator
│       │   └── JavaCodeGenerator.java
│       └── examples/            # DSL example programs
│           ├── LoginFormExample.java
│           ├── TextEditorExample.java
│           ├── SettingsFormExample.java
│           └── CodeGenExample.java
└── README.md
```

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

## Architecture & Design

### Meta-model

The meta-model captures the essential concepts of a GUI:

```
GUIElement (abstract)
├── Window          — top-level container (title, size, children)
├── Panel           — nested container (titled group of children)
└── Widget (abstract) — leaf element
    ├── Label       — static text
    ├── Button      — clickable
    ├── TextField   — single-line input
    ├── TextArea    — multi-line input
    ├── CheckBox    — boolean toggle
    ├── ComboBox    — dropdown list
    ├── Slider      — range input
    └── Separator   — visual divider line

Layout (internal)   — automatic layout (vertical stacking by default)
```

### Design Patterns Used

| Pattern             | Where                          | Purpose                                    |
|---------------------|--------------------------------|--------------------------------------------|
| **Builder**         | `WindowBuilder`, `PanelBuilder`| Fluent construction of model objects        |
| **CRTP**            | `ContainerBuilder<SELF>`       | Type-safe fluent return in inheritance      |
| **Visitor**         | `GUIVisitor`                   | Separate traversal from model structure     |
| **Interpreter**     | `SwingInterpreter`             | Execute model as live Swing GUI             |
| **Code Generator**  | `JavaCodeGenerator`            | Transform model to Java source code         |
| **Composite**       | `Window` / `Panel` / `Widget`  | Tree structure of GUI elements              |

### Internal DSL Techniques

1. **Method chaining**: Every builder method returns `this` (or `self()`) for fluent chains.
2. **Nested builders**: `.panel("title") ... .endPanel()` creates a scope that returns
   to the parent builder with the correct type.
3. **Generics (CRTP)**: `ContainerBuilder<SELF>` ensures that methods inherited from the
   base class return the concrete builder type, maintaining fluency.
4. **Terminal operations**: `.show()`, `.build()`, `.generateCode()` finalize the model
   and trigger semantics (interpretation or code generation).
5. **Strings-and-numbers only**: The user never writes Layout, Position, or lambda
   expressions — the framework handles all technical details automatically.

---

## Learning Objectives Covered

| Objective                                  | Implementation                              |
|--------------------------------------------|---------------------------------------------|
| Design a meta-model                        | `model/` package: class hierarchy, enums     |
| Class diagram & state machine diagrams     | `docs/MODELING.md`: UML Mermaid diagrams     |
| Design DSL syntax matching a meta-model    | `dsl/` package: fluent API mirrors the model |
| Express syntax using BNF                   | `docs/BNF_GRAMMAR.md`                        |
| Implement an interpreter                   | `SwingInterpreter`: model → live Swing GUI   |
| Implement a code generator                 | `JavaCodeGenerator`: model → Java source     |
| Model key concepts of a problem domain     | GUI elements, layout, hierarchy, events      |
| Automatically generated software systems   | Code gen produces standalone executables      |

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
