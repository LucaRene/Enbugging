# Enbugging Übungsprogramm

## Überblick

Dieses Projekt bietet eine interaktive Lernumgebung, in der Benutzer bestimmte Java-Fehlermeldungen bewusst herbeiführen können.

Im Startmenü kann hierbei zwischen den folgenden Aufgabentypen gewählt werden:
- **Syntaxfehler**
- **Variablen-/Deklarationsfehler**
- **Typfehler**
- **Volle Erfahrung (alle Typen)**

Nach Auswahl erfolgt eine automatische Weiterleitung zur Übungsseite mit passenden Aufgaben.


Alternativ kann das Hauptmenü übersprungen werden, indem Aufgabentypen über eine **Konfigurationsdatei** vordefiniert werden:

1. Erstelle eine Datei namens `config.txt` im Verzeichnis der Backend-JAR-Datei.
2. Jeder Eintrag entspricht einem Aufgabentyp. Eine Liste aller Fehlertypen:
```txt
SemicolonErrorTask
UnclosedStringErrorTask       
ReachedEndOfFileErrorTask
CannotFindSymbolErrorTask
ReturnTypeRequiredErrorTask
IllegalStartOfExpressionErrorTask
IntConvertToStringErrorTask
StringConvertToIntOrDoubleErrorTask
VariableAlreadyDefinedErrorTask
IdentifierExpectedErrorTask
MissingReturnValueErrorTask
MissingReturnStatementErrorTask
ConstructorArgumentMismatchErrorTask
```