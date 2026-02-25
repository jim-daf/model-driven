# QuickGUI — BNF Grammar Specification

This document defines the **abstract syntax** of the QuickGUI domain-specific
language using **Backus-Naur Form (BNF)**.  Because QuickGUI is an *internal* DSL
embedded in Java, the concrete syntax is Java method chains.  The BNF below
describes the **conceptual grammar** — the structure that the DSL enforces via
its fluent API types.

Every DSL method accepts **only strings and numbers** — no Java-specific types.

---

## Grammar

```bnf
<gui-program>    ::= "GUI.window(" <string> ")" <window-config>* <terminal>

<window-config>  ::= <size-clause>
                   | <child-element>

<size-clause>    ::= ".size(" <int> "," <int> ")"

<child-element>  ::= <label>
                   | <button>
                   | <text-field>
                   | <text-area>
                   | <check-box>
                   | <combo-box>
                   | <slider>
                   | <separator>
                   | <panel>

<label>          ::= ".label(" <string> ")"

<button>         ::= ".button(" <string> ")"

<text-field>     ::= ".textField(" <string> ")"
                   | ".textField(" <string> "," <int> ")"

<text-area>      ::= ".textArea(" <string> ")"
                   | ".textArea(" <string> "," <int> "," <int> ")"

<check-box>      ::= ".checkBox(" <string> ")"

<combo-box>      ::= ".comboBox(" <string> "," <string>+ ")"

<slider>         ::= ".slider(" <string> "," <int> "," <int> "," <int> ")"

<separator>      ::= ".separator()"

<panel>          ::= ".panel(" <string> ")" <panel-config>* ".endPanel()"

<panel-config>   ::= <child-element>

<terminal>       ::= ".show()"
                   | ".build()"
                   | ".generateCode(" <string> ")"

<string>         ::= (* any text in quotes *)
<int>            ::= (* any whole number *)
```

---

## Explanation of Grammar Structure

| Non-terminal      | Meta-model concept   | Description                              |
|-------------------|----------------------|------------------------------------------|
| `<gui-program>`   | **Window**           | Top-level window, the model root         |
| `<panel>`         | **Panel**            | Nested container (auto-titled, auto-layout) |
| `<child-element>` | **GUIElement**       | Any widget or sub-panel                  |
| `<label>`         | **Label**            | Static text display widget               |
| `<button>`        | **Button**           | Clickable widget                         |
| `<text-field>`    | **TextField**        | Single-line text input widget            |
| `<text-area>`     | **TextArea**         | Multi-line text input widget             |
| `<check-box>`     | **CheckBox**         | Boolean toggle widget                    |
| `<combo-box>`     | **ComboBox**         | Dropdown selection widget                |
| `<slider>`        | **Slider**           | Range selection widget                   |
| `<separator>`     | **Separator**        | Visual divider line                      |
| `<terminal>`      | —                    | Triggers interpretation or code gen      |

## Key Observations

1. **Strings and numbers only**: Every parameter in the grammar is either a `<string>`
   or an `<int>` — no Java-specific types, enums, or lambdas appear in the DSL.

2. **Hierarchical structure**: The grammar is inherently recursive — a `<panel>`
   can contain `<child-element>` which includes `<panel>`, enabling arbitrary nesting.

3. **Automatic layout**: Layout and positioning are handled by the framework —
   the grammar has no layout or position rules, keeping the DSL minimal.

4. **Type safety**: In the internal DSL, the Java type system enforces the grammar —
   `.endPanel()` returns to the parent builder type, so you cannot call `.show()`
   inside a panel or `.endPanel()` outside one.

5. **Fluent chaining**: Every configuration method returns `SELF` (the builder type),
   enabling the natural left-to-right reading order typical of internal DSLs.

6. **Terminal operations**: The grammar distinguishes between configuration methods
   (which return a builder) and terminal methods (`.show()`, `.build()`, `.generateCode()`)
   that finalize the model and trigger semantics.
