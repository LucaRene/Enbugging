package Context;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Concrete context class representing a vehicle. This class provides specific
 * attributes and values relevant to a vehicle, including speed, fuel level, mileage,
 * brand, model, and more.
 */
public class VehicleContext extends ContextStrategy {

    /**
     * Constructs a new VehicleContext and initializes the attribute-value
     * mappings with vehicle-specific data.
     */
    public VehicleContext() {
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

    /**
     * Returns the name of the class represented by this context.
     *
     * @return the class name, "Fahrzeug"
     */
    @Override
    public String getClassName() {
        return "Fahrzeug";
    }
}