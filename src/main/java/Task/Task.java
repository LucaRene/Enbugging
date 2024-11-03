package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task {

    private StringBuilder taskCode;
    private final ContextStrategy context;
    private final List<String> generatedAttributes;

    private String expectedErrorMessage;

    public Task(ContextStrategy context) {
        this.context = context;
        this.taskCode = new StringBuilder();
        this.generatedAttributes = new ArrayList<>();

        createClassDeclaration();
        createVariable();
        createVariable();
        createGetter();
        createSetter();
        closeClass();
    }

    public void createClassDeclaration() {
        taskCode.append("\n").append("public class ").append(context.getClassName()).append(" {").append("\n\n");
    }

    public void createVariable() {
        String attribute = context.getRandomAttribute();
        Object value = context.getRandomValueForAttribute(attribute);

        while (generatedAttributes.contains(attribute)) {
            attribute = context.getRandomAttribute();
            value = context.getRandomValueForAttribute(attribute);
        }

        String variableDeclaration = "\t" + getJavaType(value) + " " + attribute + " = " + formatValue(value) + ";";
        taskCode.append(variableDeclaration).append("\n");
        generatedAttributes.add(attribute);
    }

    public void createGetter() {
        if (generatedAttributes.isEmpty()) {
            return;
        }

        Random random = new Random();
        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
        String getter = "\n\t"+ "public " + getJavaType(context.getRandomValueForAttribute(attribute)) + " get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1) + "() {\n\t\treturn " + attribute + ";\n\t}";
        taskCode.append(getter).append("\n");
    }

    public void createSetter() {
        if (generatedAttributes.isEmpty()) {
            return;
        }

        Random random = new Random();
        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
        String setter = "\n\t"+ "public void aendere" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1) + "(" + getJavaType(context.getRandomValueForAttribute(attribute)) + " " + attribute + ") {\n\t\tthis." + attribute + " = " + attribute + ";\n\t}";
        taskCode.append(setter).append("\n");
    }

    public void closeClass() {
        taskCode.append("}\n");
    }

    private String getJavaType(Object value) {
        if (value instanceof Integer) return "int";
        if (value instanceof Double) return "double";
        if (value instanceof String) return "String";
        return "Object";
    }

    private String formatValue(Object value) {
        if (value instanceof String) return "\"" + value + "\"";
        return value.toString();
    }

    public String getTaskCode() {
        return taskCode.toString();
    }
}