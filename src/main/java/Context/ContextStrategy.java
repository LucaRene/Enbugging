package Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstract class providing a strategy for defining context-specific attributes,
 * methods, and values for generated classes. This class serves as a blueprint
 * for concrete contexts with specific attribute and method mappings.
 */
public abstract class ContextStrategy {

    protected Map<String, List<Object>> attributeValueMap = new HashMap<>();
    protected Random random = new Random();

    /**
     * Returns the name of the class that this context represents.
     * Each concrete strategy must provide its own class name.
     *
     * @return the name of the class as a String
     */
    public abstract String getClassName();

    /**
     * Selects a random attribute from the available attributes in the context.
     *
     * @return the name of a randomly selected attribute as a String
     */
    public String getRandomAttribute() {
        Object[] keys = attributeValueMap.keySet().toArray();
        return (String) keys[random.nextInt(keys.length)];
    }

    /**
     * Selects a random value for a specified attribute.
     * This method assumes the attribute has values defined in the attributeValueMap.
     *
     * @param attribute the attribute for which a value is selected
     * @return a randomly selected value for the attribute
     */
    public Object getRandomValueForAttribute(String attribute) {
        List<Object> values = attributeValueMap.get(attribute);
        return values.get(random.nextInt(values.size()));
    }
}