package Context;

import java.util.Arrays;

public class PersonContext extends ContextStrategy {

    public PersonContext() {
        attributeMethodMap.put("name", new String[]{"aendereName", "zeigeName"});
        attributeMethodMap.put("alter", new String[]{"aendereAlter", "zeigeAlter"});
        attributeMethodMap.put("geschlecht", new String[]{"zeigeGeschlecht"});
        attributeMethodMap.put("nationalitaet", new String[]{"zeigeNationalitaet"});
        attributeMethodMap.put("gewicht", new String[]{"aendereGewicht", "zeigeGewicht"});
        attributeMethodMap.put("groesse", new String[]{"aendereGroesse", "zeigeGroesse"});
        attributeMethodMap.put("beruf", new String[]{"aendereBeruf", "zeigeBeruf"});
        attributeMethodMap.put("einkommen", new String[]{"aendereEinkommen", "zeigeEinkommen"});
        attributeMethodMap.put("wohnort", new String[]{"aendereWohnort", "zeigeWohnort"});

        attributeValueMap.put("name", Arrays.asList("Max", "Anna", "Paul", "Marie", "Lukas"));
        attributeValueMap.put("alter", Arrays.asList(20, 25, 30, 35, 40));
        attributeValueMap.put("geschlecht", Arrays.asList("männlich", "weiblich", "divers"));
        attributeValueMap.put("nationalitaet", Arrays.asList("deutsch", "französisch", "britisch", "spanisch", "italienisch"));
        attributeValueMap.put("gewicht", Arrays.asList(60, 70, 80, 90, 100));
        attributeValueMap.put("groesse", Arrays.asList(1.60, 1.70, 1.80, 1.90, 2.00));
        attributeValueMap.put("beruf", Arrays.asList("Ingenieur", "Lehrer", "Arzt", "Jurist", "Künstler"));
        attributeValueMap.put("einkommen", Arrays.asList(30000, 40000, 50000, 60000, 70000));
        attributeValueMap.put("wohnort", Arrays.asList("Berlin", "München", "Hamburg", "Köln", "Frankfurt"));
    }

    @Override
    public String getClassName() {
        return "Person";
    }
}
