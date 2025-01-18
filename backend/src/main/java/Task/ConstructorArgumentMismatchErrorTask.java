package Task;

import Context.ContextStrategy;

import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a
 * "constructor ... cannot be applied to given types" error.
 */
public class ConstructorArgumentMismatchErrorTask extends Task {

    /**
     * Constructs a new ConstructorArgumentMismatchErrorTask with the specified context.
     *
     * @param context  the context used to generate attributes and methods
     * @param gapCount the number of gaps to create in the task
     */
    public ConstructorArgumentMismatchErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "constructor " + context.getClassName() + " in class " + context.getClassName() +
                " cannot be applied to given types;\n" +
                "  required: " + generateExpectedConstructorSignature() + "\n" +
                "  found: " + generateActualConstructorCallSignature() + "\n" +
                "  reason: actual and formal argument lists differ in length";
    }


    /**
     * Generates a specified task code with a constructor and a main method.
     * This is obligatory to be able to solve the task.
     *
     * @return true if the task code was generated successfully, false otherwise
     */
    @Override
    public boolean generateTaskCode() {
        logger.info("Generating task code...");
        Random random = new Random();

        createClassDeclaration();

        int variableCount = random.nextInt(2) + 1;
        if (createVariables(variableCount)) {
            logger.info("Variables generated successfully.");
        } else {
            logger.warning("Variable generation failed.");
            return false;
        }

        generateConstructor();

        int methodCount = random.nextInt(2);
        if (generateMethods(methodCount)) {
            logger.info("Methods generated successfully.");
        } else {
            logger.warning("Method generation failed.");
            return false;
        }

        createMainMethod();

        closeClass();
        logger.info("Task code generation complete.");
        return true;
    }

    /**
     * Creates a gap in the Main method by replacing one parameter in the constructor call
     * with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     * @return true if a gap was created successfully, false otherwise
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        String constructorCall = "new " + context.getClassName() + "(";
        int constructorPosition = code.indexOf(constructorCall);

        if (constructorPosition == -1) {
            logger.warning("Constructor call not found in the Main method.");
            return false;
        }

        int paramStart = constructorPosition + constructorCall.length();
        int replaceEnd;

        if (getGeneratedAttributes().size() == 2){
            replaceEnd = code.indexOf(",", paramStart);
        } else {
            replaceEnd = code.indexOf(")", paramStart);
        }

        if (paramStart == -1 || replaceEnd == -1) {
            logger.warning("Parameter list for constructor call not found.");
            return false;
        }

        if (getGeneratedAttributes().size() == 2){
            replaceEnd += 1;
        }

        String toReplace = code.substring(paramStart, replaceEnd);
        code.replace(paramStart, replaceEnd, "[[" + toReplace + "]]");

        logger.info("Solution gap created in Main method constructor call: " + toReplace);
        return true;
    }

    /**
     * Generates the expected constructor signature based on the generated attributes.
     *
     * @return A string representing the expected constructor parameter types.
     */
    private String generateExpectedConstructorSignature() {
        StringBuilder expectedSignature = new StringBuilder();

        for (String attribute : generatedAttributes) {
            String type = getJavaType(context.getRandomValueForAttribute(attribute));
            if (type.equals("String")){
                type = "java.lang.String";
            }
            expectedSignature.append(type).append(",");
        }

        if (expectedSignature.length() > 1) {
            expectedSignature.setLength(expectedSignature.length() - 1);
        } else {
            expectedSignature = new StringBuilder("no arguments");
        }

        return expectedSignature.toString();
    }

    /**
     * Generates the actual constructor call signature based on the generated attributes.
     * This signature includes one less parameter than required to trigger the error.
     *
     * @return A string representing the actual constructor parameter types.
     */
    private String generateActualConstructorCallSignature() {
        StringBuilder actualSignature = new StringBuilder();

        if (getGeneratedAttributes().size() == 2){
            String attribute = generatedAttributes.get(1);
            String type = getJavaType(context.getRandomValueForAttribute(attribute));
            if (type.equals("String")){
                type = "java.lang.String";
            }
            actualSignature.append(type);
        } else {
            actualSignature.append("no arguments");
        }
        return actualSignature.toString();
    }
}