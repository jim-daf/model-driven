# QuickGUI External DSL — EBNF Grammar

## EBNF

```ebnf
(* ===== QuickGUI Grammar ===== *)

Program        = Window ;

Window         = "window" STRING [ SizeClause ] "{" { Element } "}" ;

SizeClause     = "size" "(" INTEGER "," INTEGER ")" ;

Element        = Label
               | Button
               | TextField
               | TextArea
               | CheckBox
               | ComboBox
               | Slider
               | Separator
               | Panel ;

(* --- Widgets --- *)

Label          = "label" STRING ;

Button         = "button" STRING ;

TextField      = "textField" STRING [ ColumnsClause ] ;

TextArea       = "textArea" STRING [ RowsClause ] [ ColsClause ] ;

CheckBox       = "checkBox" STRING ;

ComboBox       = "comboBox" STRING ItemsClause ;

Slider         = "slider" STRING RangeClause ValueClause ;

Separator      = "separator" ;

(* --- Panels (nested containers) --- *)

Panel          = "panel" STRING "{" { Element } "}" ;

(* --- Attribute Clauses --- *)

ColumnsClause  = "columns" "(" INTEGER ")" ;

RowsClause     = "rows" "(" INTEGER ")" ;

ColsClause     = "cols" "(" INTEGER ")" ;

ItemsClause    = "items" "(" STRING { "," STRING } ")" ;

RangeClause    = "range" "(" INTEGER "," INTEGER ")" ;

ValueClause    = "value" "(" INTEGER ")" ;

(* --- Terminals --- *)

STRING         = '"' { CHARACTER } '"' ;

INTEGER        = DIGIT { DIGIT } ;

CHARACTER      = ? any printable character except '"' ? | '\"' ;

DIGIT          = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
```

## Example Program (login_form.qg)

```
window "Login" size(350, 250) {
    label "Please sign in"

    panel "Credentials" {
        label "Username:"
        textField "username" columns(15)
        label "Password:"
        textField "password" columns(15)
        checkBox "Remember me"
    }

    button "Login"
    button "Cancel"
}
```

## How the EBNF Describes the Example

| Example fragment                    | EBNF rule applied                                                |
|-------------------------------------|------------------------------------------------------------------|
| `window "Login" size(350, 250) {…}` | `Window → "window" STRING SizeClause "{" { Element } "}"`       |
| `size(350, 250)`                    | `SizeClause → "size" "(" INTEGER "," INTEGER ")"`               |
| `label "Please sign in"`           | `Element → Label → "label" STRING`                               |
| `panel "Credentials" {…}`          | `Element → Panel → "panel" STRING "{" { Element } "}"`          |
| `textField "username" columns(15)` | `Element → TextField → "textField" STRING ColumnsClause`         |
| `checkBox "Remember me"`           | `Element → CheckBox → "checkBox" STRING`                         |
| `button "Login"`                   | `Element → Button → "button" STRING`                             |

## Mapping from Internal DSL to External DSL

| Internal DSL (Java fluent API)                    | External DSL (`.qg` file)                           |
|---------------------------------------------------|------------------------------------------------------|
| `GUI.window("Title")`                             | `window "Title" { … }`                               |
| `.size(400, 300)`                                 | `size(400, 300)`                                      |
| `.label("text")`                                  | `label "text"`                                        |
| `.button("text")`                                 | `button "text"`                                       |
| `.textField("name", 15)`                          | `textField "name" columns(15)`                        |
| `.textArea("name", 20, 60)`                       | `textArea "name" rows(20) cols(60)`                   |
| `.checkBox("text")`                               | `checkBox "text"`                                     |
| `.comboBox("name", "A", "B", "C")`                | `comboBox "name" items("A", "B", "C")`                |
| `.slider("name", 8, 32, 14)`                      | `slider "name" range(8, 32) value(14)`                |
| `.separator()`                                    | `separator`                                           |
| `.panel("title") … .endPanel()`                   | `panel "title" { … }`                                 |
| `.show()`                                         | *(implicit — the file is interpreted when loaded)*    |
