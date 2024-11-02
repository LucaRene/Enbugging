package Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class ContextStrategy {

    protected Map<String, String[]> attributeMethodMap = new HashMap<>();
    protected Map<String, List<Object>> attributeValueMap = new HashMap<>();
    protected Random random = new Random();

    public abstract String getClassName();

    public String getRandomAttribute() {
        Object[] keys = attributeMethodMap.keySet().toArray();
        return (String) keys[random.nextInt(keys.length)];
    }

    public String getRandomMethodForAttribute(String attribute) {
        String[] methods = attributeMethodMap.get(attribute);
        return methods[random.nextInt(methods.length)];
    }

    public Object getRandomValueForAttribute(String attribute) {
        List<Object> values = attributeValueMap.get(attribute);
        return values.get(random.nextInt(values.size()));
    }
}
