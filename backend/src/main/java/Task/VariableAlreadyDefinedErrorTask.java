package Task;

import Context.ContextStrategy;

import java.util.List;
import java.util.Random;

public class VariableAlreadyDefinedErrorTask extends Task {


    /**
     * Constructs a new VariableAlreadyDefinedErrorTask and ensures that the generated
     * code contains at least two variables of the same type to allow for gap creation.
     *
     * @param context  The context strategy for generating attributes and methods.
     * @param gapCount The number of gaps to introduce in the code.
     */
    public VariableAlreadyDefinedErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
    }

    /**
     * Generates the task code with gaps.
     * Ensures that the generated code contains at least two variables of the same type.
     *
     * @return true if the task code was generated successfully, false otherwise.
     */
    @Override
    public boolean createVariables(int variableCount) {
        variableCount = 2;
        logger.info("Number of variables to generate: " + variableCount);
        createVariable();
        createVariable();

        return checkForSameTypes(this.generatedAttributes.get(0), this.generatedAttributes.get(1));
    }

    /**
     * Generates the expected error message based on the generated attributes.
     */
    @Override
    protected void setExpectedErrorMessage() {
        Random random = new Random();
        if (random.nextBoolean()) {
            expectedErrorMessage = "variable " + this.getGeneratedAttributes().get(0) + " is already defined";
        } else {
            expectedErrorMessage = "variable " + this.getGeneratedAttributes().get(1) + " is already defined";
        }
    }

    /**
     * Checks if the generated attributes are of the same type.
     *
     * @return true if the generated attributes are of the same type, false otherwise.
     */
    public boolean checkForSameTypes(String variable1, String variable2) {
        String type1 = getJavaType(context.getRandomValueForAttribute(variable1));
        String type2 = getJavaType(context.getRandomValueForAttribute(variable2));
        return type1.equals(type2);
    }


    /**
     * Introduces a gap in the code by replacing a variableName with a placeholder.
     *
     * @param code   The code snippet to modify by introducing a gap.
     * @param random A random number generator for selecting positions.
     * @return true if a gap was successfully created, false otherwise.
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        if (generatedAttributes.isEmpty()) {
            logger.warning("No generated attributes available. Gap creation aborted.");
            return false;
        } else if (generatedMethods.isEmpty()) {
            logger.warning("No generated methods available. Gap creation aborted.");
            return false;
        }

        List<Integer> positions;
        String toReplace = "";
        if (this.expectedErrorMessage.contains(generatedAttributes.get(0))) {
            positions = findAllOccurrencesOfWords(code.toString(), generatedAttributes.get(1));
            toReplace = generatedAttributes.get(1);
        } else {
            positions = findAllOccurrencesOfWords(code.toString(), generatedAttributes.get(0));
            toReplace = generatedAttributes.get(0);
        }

        int position = positions.get(0);
        code.replace(position, position + toReplace.length(), "[" + toReplace + "]");
        logger.info("Solution gap created at position: " + position);
        return true;
    }
}
