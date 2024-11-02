package Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task {

    private String expectedErrorMessage;
    private String taskCode;

    private List<String> usedVariableNames;

    public Task() {
        this.usedVariableNames = new ArrayList<>();
    }

    public String getNewTask() {
        taskCode = createClassDeclaration() + "\n" + createVariable() + "\n" + createMethod() + "\n}";
        return taskCode;
    }

    public String createClassDeclaration() {
        return "public class " + getRandomClassName() + " {";
    }

    private String getRandomClassName() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder className = new StringBuilder();
        Random random = new Random();
        int length = 5;

        for (int i = 0; i < length; i++) {
            className.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return className.toString();
    }

    public String createVariable() {
        return "\tint " + getRandomVariableName() + " = 0;";
    }

    private String getRandomVariableName() {
        String[] attributes = {
                "groesse", "gewicht", "alter", "breite", "hoehe", "tiefe", "geschwindigkeit", "temperatur",
                "druck", "laenge", "volumen", "kapazitaet", "leistung", "energie", "zeit", "frequenz", "spannung",
                "strom", "widerstand", "kraft", "drehmoment", "beschleunigung", "impuls", "arbeit", "punktestand",
                "einkommen", "kinderzahl", "haustier", "anzahlBaeume", "anzahlAutos", "anzahlZimmer", "anzahlStockwerke",
                "anzahlFenster", "anzahlTueren", "anzahlSchueler", "anzahlMitarbeiter", "anzahlBesucher", "anzahlTeilnehmer"
        };
        Random random = new Random();
        return attributes[random.nextInt(attributes.length)];
    }

    public String createMethod() {
        return "\tpublic void methodName() {\n\n\t}";
    }
}
