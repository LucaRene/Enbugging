package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The Task class generates a random, syntactically correct Java class code snippet.
 * It utilizes a specified context to generate attributes and methods for the class.
 */
public abstract class Task {

    protected static final Logger logger = Logger.getLogger(Task.class.getName());

    protected final ContextStrategy context;

    protected final StringBuilder taskCodeWithoutGaps;
    protected final StringBuilder taskCodeWithGaps;

    protected final List<String> generatedAttributes;
    protected final List<String> generatedMethods;

    protected final int gapCount;
    protected String expectedErrorMessage;

    /**
     * Constructs a new Task with the specified context, generating a random class
     * with variables, getter and setter methods, based on context attributes.
     *
     * @param context the context used to generate attributes and methods
     */
    public Task(ContextStrategy context, int gapCount) {
        this.context = context;
        this.taskCodeWithoutGaps = new StringBuilder();
        this.taskCodeWithGaps = new StringBuilder();
        this.generatedAttributes = new ArrayList<>();
        this.generatedMethods = new ArrayList<>();
        this.gapCount = gapCount;

        logger.info("Task creation started.");
        logger.info("Context: " + context.getClassName());
        logger.info("Gap count: " + gapCount);

        while (!generateTaskCode()) {
            logger.warning("Task generation failed. Resetting task...");
            resetTask();
        }
        logger.info("Task code generated without gaps: \n" + taskCodeWithoutGaps);

        setExpectedErrorMessage();

        while (!createGapsInCode()) {
            logger.warning("Gaps could not be created. Resetting task...");
            resetTask();
            generateTaskCode();
        }
        logger.info("Task code with gaps: \n" + taskCodeWithGaps);

        logger.info("Task creation finished.");
    }

    /**
     * Resets the task by clearing all generated code and attributes.
     */
    public void resetTask() {
        taskCodeWithoutGaps.setLength(0);
        taskCodeWithGaps.setLength(0);
        generatedAttributes.clear();
        generatedMethods.clear();
    }

    /**
     * Generates a random class with variables, getter and setter methods, based on context attributes.
     */
    public boolean generateTaskCode() {
        logger.info("Generating task code...");
        Random random = new Random();

        createClassDeclaration();

        int variableCount = random.nextInt(3) + 1;
        if (createVariables(variableCount)) {
            logger.info("Variables generated successfully.");
        } else {
            logger.warning("Variable generation failed.");
            return false;
        }

        int methodCount = random.nextInt(2) + 1;
        if (generateMethods(methodCount)) {
            logger.info("Methods generated successfully.");
        } else {
            logger.warning("Method generation failed.");
            return false;
        }

        generateConstructor();
        createMainMethod();

        closeClass();
        logger.info("Task code generation complete.");
        return true;
    }

    /**
     * Generates a random number of variables for the class.
     *
     * @param variableCount the number of variables to generate
     * @return true if variables were created successfully, false otherwise
     */
    public boolean createVariables(int variableCount) {
        logger.info("Number of variables to generate: " + variableCount);
        for (int i = 0; i < variableCount; i++) {
            createVariable();
        }
        return true;
    }

    /**
     * Generates a random number of methods for the class.
     *
     * @param methodCount the number of methods to generate
     * @return true if methods were created successfully, false otherwise
     */
    public boolean generateMethods(int methodCount){
        logger.info("Number of methods to generate: " + methodCount);

        Random random = new Random();
        boolean getterProhibited = false;
        boolean setterProhibited = false;
        for (int i = 0; i < methodCount; i++) {
            if (getterProhibited && setterProhibited) {
                break;
            } else if (getterProhibited) {
                setterProhibited = !createSetter();
                if (setterProhibited) {
                    i--;
                }
            } else if (setterProhibited) {
                getterProhibited = !createGetter();
                if (getterProhibited) {
                    i--;
                }
            } else {
                if (random.nextBoolean()) {
                    getterProhibited = !createGetter();
                    if (getterProhibited) {
                        i--;
                    }
                } else {
                    setterProhibited = !createSetter();
                    if (setterProhibited) {
                        i--;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Generates the class declaration based on the context class name.
     */
    public void createClassDeclaration() {
        logger.info("Creating class declaration...");
        taskCodeWithoutGaps.append("public class ").append(context.getClassName()).append(" {\n\n");
        logger.info("Class declaration added.");
    }

    /**
     * Generates a variable for the class using a random attribute and assigns a random value.
     * Ensures that each generated attribute is unique within the class.
     */
    public void createVariable() {
        logger.info("Creating variable...");
        String attribute = context.getRandomAttribute();
        Object value = context.getRandomValueForAttribute(attribute);

        while (generatedAttributes.contains(attribute)) {
            logger.warning("Duplicate attribute detected: " + attribute + ". Selecting a new one...");
            attribute = context.getRandomAttribute();
            value = context.getRandomValueForAttribute(attribute);
        }

        String variableDeclaration = "\t" + getJavaType(value) + " " + attribute + " = " + formatValue(value) + ";";
        taskCodeWithoutGaps.append(variableDeclaration).append("\n");
        generatedAttributes.add(attribute);

        logger.info("Variable added: " + variableDeclaration);
    }

    /**
     * Generates a getter method for a randomly selected attribute from the list
     * of generated attributes. Does nothing if no attributes are available.
     *
     * @return true if a getter was created, false otherwise
     */
    public boolean createGetter() {
        if (generatedAttributes.isEmpty()) {
            logger.warning("No attributes available to create getter.");
            return false;
        }

        logger.info("Creating getter method...");

        int currentGetters = 0;
        for (String method : generatedMethods) {
            if (method.contains("get")) {
                currentGetters++;
            }
        }

        if (currentGetters >= generatedAttributes.size()) {
            logger.info("Current Getters: " + currentGetters + " | Attributes: " + generatedAttributes.size());
            logger.info("All possible getters already exist. Skipping getter creation.");
            return false;
        }

        Random random = new Random();
        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));

        int attempts = 0;
        while (generatedMethods.contains("get" + capitalize(attribute))) {
            logger.warning("Getter for attribute already exists: " + attribute);
            attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
            attempts++;

            if (attempts >= generatedAttributes.size() + 25) {
                logger.warning("Maximum attempts reached. Could not create a unique getter.");
                return false;
            }
        }

        String getter = "\n\tpublic " + getJavaType(context.getRandomValueForAttribute(attribute)) +
                " get" + capitalize(attribute) + "() {\n\t\treturn " + attribute + ";\n\t}";
        taskCodeWithoutGaps.append(getter).append("\n");

        generatedMethods.add("get" + capitalize(attribute));
        logger.info("Getter added: " + getter);

        return true;
    }

    /**
     * Generates a setter method for a randomly selected attribute from the list
     * of generated attributes. Does nothing if no attributes are available.
     *
     * @return true if a setter was created, false otherwise
     */
    public boolean createSetter() {
        if (generatedAttributes.isEmpty()) {
            logger.warning("No attributes available to create setter.");
            return false;
        }

        int currentSetters = 0;
        for (String method : generatedMethods) {
            if (method.contains("set")) {
                currentSetters++;
            }
        }

        logger.info("Creating setter method...");
        if (currentSetters >= generatedAttributes.size()) {
            logger.info("Current Setters: " + currentSetters + " | Attributes: " + generatedAttributes.size());
            logger.info("All possible setters already exist. Skipping setter creation.");
            return false;
        }

        Random random = new Random();
        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));

        int attempts = 0;
        while (generatedMethods.contains("set" + capitalize(attribute))) {
            logger.warning("Setter for attribute already exists: " + attribute);
            attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
            attempts++;

            if (attempts >= generatedAttributes.size() + 25) {
                logger.warning("Maximum attempts reached. Could not create a unique setter.");
                return false;
            }
        }

        String setter = "\n\tpublic void set" + capitalize(attribute) +
                "(" + getJavaType(context.getRandomValueForAttribute(attribute)) + " " + attribute + "Neu" +
                ") {\n\t\tthis." + attribute + " = " + attribute + "Neu" + ";\n\t}";
        taskCodeWithoutGaps.append(setter).append("\n");

        generatedMethods.add("set" + capitalize(attribute));
        logger.info("Setter added: " + setter);

        return true;
    }

    /**
     * Generates a constructor for the class.
     *
     * @return true if the constructor was created, false otherwise
     */
    public boolean generateConstructor() {
        logger.info("Generating constructor...");

        StringBuilder parameter = new StringBuilder();
        for (String attribute : generatedAttributes) {
            parameter.append(getJavaType(context.getRandomValueForAttribute(attribute)))
                    .append(" ").append(attribute).append(", ");
        }
        parameter.setLength(parameter.length() - 2);

        taskCodeWithoutGaps.append("\n\tpublic ").append(context.getClassName())
                .append("(" + parameter.toString() + ") {\n");
        for (String attribute : generatedAttributes) {
            taskCodeWithoutGaps.append("\t\tthis.").append(attribute).append(" = ").append(attribute).append(";\n");
        }

        taskCodeWithoutGaps.append("\t}\n");
        logger.info("Constructor generated.");
        return true;
    }

    /**
     * Creates the main method for the class.
     *
     * @return true if the main method was created, false otherwise
     */
    public boolean createMainMethod() {
        logger.info("Creating main method...");
        taskCodeWithoutGaps.append("\n\tpublic static void main(String[] args) {\n");

        StringBuilder values = new StringBuilder();
        for (String attribute : generatedAttributes) {
            String type = getJavaType(context.getRandomValueForAttribute(attribute));
            if (type.equals("String")) {
                values.append("\"").append(context.getRandomValueForAttribute(attribute)).append("\", ");
            } else {
                values.append(context.getRandomValueForAttribute(attribute)).append(", ");
            }
        }
        values.setLength(values.length() - 2);

        taskCodeWithoutGaps.append("\t\t").append(context.getClassName()).append(" obj = new ")
                .append(context.getClassName()).append("(").append(values).append(");\n");
        taskCodeWithoutGaps.append("\t}\n");
        logger.info("Main method created.");
        return true;
    }

    /**
     * Closes the generated class code with a closing brace.
     */
    public void closeClass() {
        logger.info("Closing class...");
        taskCodeWithoutGaps.append("}");
        logger.info("Class closed.");
    }

    /**
     * Creates gaps in the generated code by replacing selected keywords or symbols with gaps.
     *
     * @return true if gaps were created successfully, false otherwise
     */
    protected boolean createGapsInCode() {
        logger.info("Creating gaps in the code...");
        Random random = new Random();
        StringBuilder code = new StringBuilder(taskCodeWithoutGaps);
        List<String> words = new ArrayList<>(Arrays.stream(code.toString()
                        .split("(?<=;)|(?=;)|(?<=\\()|(?=\\()|(?<=\\))|(?=\\))|\\s+"))
                .filter(word -> !word.isEmpty())
                .toList());

        if (!createSolutionGap(code, random)) {
            logger.warning("Solution gap could not be created.");
            return false;
        }

        for (int i = 0; i < gapCount - 1; i++) {
            int index = random.nextInt(words.size());
            String gap = words.get(index);

            while (gap.isEmpty() || gap.isBlank()) {
                index = random.nextInt(words.size());
                gap = words.get(index);
            }

            List<Integer> positions = findAllOccurrencesOfWords(code.toString(), gap);

            if (positions.isEmpty()) {
                logger.warning("No positions found for word: " + gap);
                i--;
                continue;
            }

            int position = positions.get(random.nextInt(positions.size()));

            if (position != 0 && code.charAt(position - 1) == '[') {
                logger.warning("Position already contains a gap: " + gap);
                i--;
                continue;
            }

            code.replace(position, position + gap.length(), "[[" + gap + "]]");
            words.remove(index);
        }

        taskCodeWithGaps.setLength(0);
        taskCodeWithGaps.append(code);

        logger.info("Gaps created successfully.");
        return true;
    }

    /**
     * Sets the expected error message for this task.
     */
    protected void setExpectedErrorMessage() {
        logger.info("Setting expected error message...");
        expectedErrorMessage = "Error message not set.";
        logger.info("Expected error message set: " + expectedErrorMessage);
    }

    /**
     * Creates a gap in the code by replacing a semicolon with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     * @return true if a gap was created, false otherwise
     */
    protected abstract boolean createSolutionGap(StringBuilder code, Random random);

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
        if (value instanceof Boolean) return "boolean";
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
     * Finds all occurrences of a substring within a string.
     *
     * @param text the text to search for the substring
     * @param sub  the substring to find within the text
     * @return a list of positions where the substring occurs in the text
     */
    protected List<Integer> findAllOccurrencesOfWords(String text, String sub) {
        List<Integer> positions = new ArrayList<>();
        int index = text.indexOf(sub);
        while (index >= 0) {
            positions.add(index);
            index = text.indexOf(sub, index + sub.length());
        }
        return positions;
    }

    /**
     * Returns the complete generated code for the class as a String.
     *
     * @return the generated class code
     */
    public String getTaskCodeWithGaps() {
        return taskCodeWithGaps.toString();
    }

    /**
     * Returns the name of the generated class.
     *
     * @return the name of the generated class
     */
    public String getTaskCodeWithoutGaps() {
        return taskCodeWithoutGaps.toString();
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
