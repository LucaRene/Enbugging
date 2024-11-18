package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Task class generates a random, syntactically correct Java class code snippet.
 * It utilizes a specified context to generate attributes and methods for the class.
 */
public abstract class Task {

    protected final StringBuilder taskCodeWithoutGaps;
    protected final StringBuilder taskCodeWithGaps;
    protected final ContextStrategy context;
    protected final List<String> generatedAttributes;
    protected String expectedErrorMessage;

    /**
     * Constructs a new Task with the specified context, generating a random class
     * with variables, getter and setter methods, based on context attributes.
     *
     * @param context the context used to generate attributes and methods
     */
    public Task(ContextStrategy context) {
        this.context = context;
        this.taskCodeWithoutGaps = new StringBuilder();
        this.taskCodeWithGaps = new StringBuilder();
        this.generatedAttributes = new ArrayList<>();

        createClassDeclaration();
        createVariable();
        createVariable();
        createGetter();
        createSetter();
        closeClass();
    }

    /**
     * Generates the class declaration based on the context class name.
     */
    public void createClassDeclaration() {
        taskCodeWithoutGaps.append("public class ").append(context.getClassName()).append(" {").append("\n\n");
    }

    /**
     * Generates a variable for the class using a random attribute and assigns a random value.
     * Ensures that each generated attribute is unique within the class.
     */
    public void createVariable() {
        String attribute = context.getRandomAttribute();
        Object value = context.getRandomValueForAttribute(attribute);

        while (generatedAttributes.contains(attribute)) {
            attribute = context.getRandomAttribute();
            value = context.getRandomValueForAttribute(attribute);
        }

        String variableDeclaration = "\t" + getJavaType(value) + " " + attribute + " = " + formatValue(value) + ";";
        taskCodeWithoutGaps.append(variableDeclaration).append("\n");
        generatedAttributes.add(attribute);
    }

    /**
     * Generates a getter method for a randomly selected attribute from the list
     * of generated attributes. Does nothing if no attributes are available.
     */
    public void createGetter() {
        if (generatedAttributes.isEmpty()) {
            return;
        }

        Random random = new Random();
        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
        String getter = "\n\tpublic " + getJavaType(context.getRandomValueForAttribute(attribute)) +
                " get" + capitalize(attribute) + "() {\n\t\treturn " + attribute + ";\n\t}";
        taskCodeWithoutGaps.append(getter).append("\n");
    }

    /**
     * Generates a setter method for a randomly selected attribute from the list
     * of generated attributes. Does nothing if no attributes are available.
     */
    public void createSetter() {
        if (generatedAttributes.isEmpty()) {
            return;
        }

        Random random = new Random();
        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
        String setter = "\n\tpublic void set" + capitalize(attribute) +
                "(" + getJavaType(context.getRandomValueForAttribute(attribute)) + " " + attribute +
                ") {\n\t\tthis." + attribute + " = " + attribute + ";\n\t}";
        taskCodeWithoutGaps.append(setter).append("\n");
    }

    /**
     * Closes the generated class code with a closing brace.
     */
    public void closeClass() {
        taskCodeWithoutGaps.append("}\n");
    }

    protected abstract void createGapsInCode();

    /**
     * Determines the Java data type of the provided value.
     *
     * @param value the value whose type is to be determined
     * @return a String representing the Java data type
     */
    public String getJavaType(Object value) {
        if (value instanceof Integer) return "int";
        if (value instanceof Double) return "double";
        if (value instanceof String) return "String";
        return "Object";
    }

    /**
     * Formats the given value for use in Java code. Adds quotes for String values.
     *
     * @param value the value to be formatted
     * @return a String representation of the value, formatted for Java code
     */
    public String formatValue(Object value) {
        if (value instanceof String) return "\"" + value + "\"";
        return value.toString();
    }

    /**
     * Capitalizes the first letter of the provided string.
     *
     * @param str the string to capitalize
     * @return the capitalized string
     */
    public String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Returns the complete generated code for the class as a String.
     *
     * @return the generated class code
     */
    public String getTaskCode() {
        return taskCodeWithGaps.toString();
    }

    /**
     * Returns the name of the generated class.
     *
     * @return the name of the generated class
     */
    public String getClassName() {
        return context.getClassName();
    }

    /**
     * Retrieves a list of all generated attributes for this class.
     *
     * @return a list of generated attribute names
     */
    public List<String> getGeneratedAttributes() {
        return new ArrayList<>(generatedAttributes);
    }

    /**
     * Retrieves the expected error message for this task.
     *
     * @return the expected error message
     */
    public String getExpectedErrorMessage() {
        return expectedErrorMessage;
    }
}
