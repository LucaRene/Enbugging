package Context;

import java.util.Arrays;
import java.util.HashMap;


public class VehicleContext extends ContextStrategy {

    public VehicleContext() {
        // Attribut-Methoden-Verknüpfung
        attributeMethodMap = new HashMap<>();
        attributeMethodMap.put("geschwindigkeit", new String[]{"beschleunige", "bremse"});
        attributeMethodMap.put("tankfuellstand", new String[]{"tankeAuf", "verbraucheKraftstoff"});
        attributeMethodMap.put("kilometerstand", new String[]{"erhoeheKilometerstand"});
        attributeMethodMap.put("marke", new String[]{"zeigeMarke", "aendereMarke"});
        attributeMethodMap.put("modell", new String[]{"zeigeModell", "aendereModell"});
        attributeMethodMap.put("typ", new String[]{"zeigeTyp", "aendereTyp"});
        attributeMethodMap.put("baujahr", new String[]{"zeigeBaujahr", "aendereBaujahr"});
        attributeMethodMap.put("farbe", new String[]{"zeigeFarbe", "aendereFarbe"});
        attributeMethodMap.put("sitzplaetze", new String[]{"zeigeSitzplaetze", "aendereSitzplaetze"});
        attributeMethodMap.put("kennzeichen", new String[]{"zeigeKennzeichen", "aendereKennzeichen"});

        // Attribut-Wert-Verknüpfung
        attributeValueMap = new HashMap<>();
        attributeValueMap.put("geschwindigkeit", Arrays.asList(0, 50, 100, 150, 200, 250, 300));
        attributeValueMap.put("tankfuellstand", Arrays.asList(0, 25, 50, 75, 100));
        attributeValueMap.put("kilometerstand", Arrays.asList(0, 10000, 20000, 50000, 100000, 150000, 200000));
        attributeValueMap.put("marke", Arrays.asList("Audi", "BMW", "Mercedes", "Volkswagen", "Toyota", "Ford", "Tesla"));
        attributeValueMap.put("modell", Arrays.asList("A4", "3er", "S-Klasse", "Golf", "Corolla", "Mustang", "Model S"));
        attributeValueMap.put("typ", Arrays.asList("Limousine", "SUV", "Cabrio", "Kombi", "Pickup", "Van", "Motorrad"));
        attributeValueMap.put("baujahr", Arrays.asList(2000, 2005, 2010, 2015, 2020, 2022));
        attributeValueMap.put("farbe", Arrays.asList("Rot", "Blau", "Schwarz", "Weiß", "Silber", "Grün", "Gelb"));
        attributeValueMap.put("sitzplaetze", Arrays.asList(2, 4, 5, 7, 9));
        attributeValueMap.put("kennzeichen", Arrays.asList("M-AB1234", "B-CD5678", "HH-EF9012", "F-GH3456", "S-IJ7890"));
    }

    @Override
    public String getClassName() {
        return "Fahrzeug";
    }
}
