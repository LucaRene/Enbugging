package Context;

import java.util.Arrays;

/**
 * Concrete context class for representing a person. This class provides specific
 * attributes, methods, and values relevant to a person, such as name, age, gender,
 * nationality, and more.
 */
public class PersonContext extends ContextStrategy {

    /**
     * Constructs a new PersonContext and initializes the attribute-method and
     * attribute-value mappings with person-specific information.
     */
    public PersonContext() {
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

    /**
     * Returns the name of the class represented by this context.
     *
     * @return the class name, "Person"
     */
    @Override
    public String getClassName() {
        return "Person";
    }
}
