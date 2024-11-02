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
        createMethod();
        closeClass();
    }

    public void createClassDeclaration() {
        taskCode.append("public class ").append(context.getClassName()).append(" {").append("\n");
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

    public void createMethod() {
        if (generatedAttributes.isEmpty()) {
            return;
        }

        Random random = new Random();
        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
        String method = context.getRandomMethodForAttribute(attribute);

        String methodDeclaration = "\tpublic void " + method + "() {\n\t\t// Logik für " + attribute + "\n\t}";
        taskCode.append(methodDeclaration).append("\n");
    }

    public void closeClass() {
        taskCode.append("}");
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