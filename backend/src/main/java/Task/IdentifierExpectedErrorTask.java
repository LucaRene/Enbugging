package Task;

import Context.ContextStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing an
 * "<identifier> expected" error.
 */
public class IdentifierExpectedErrorTask extends Task {

    /**
     * Constructs a new IdentifierExpectedErrorTask and ensures that the generated
     * code contains valid locations to create gaps that trigger the expected error.
     *
     * @param context  The context strategy for generating attributes and methods.
     * @param gapCount The number of gaps to introduce in the code.
     */
    public IdentifierExpectedErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "<identifier> expected";
        hintMessage = "Diese Fehlermeldung tritt auf, wenn der Nutzer versucht eine Variable/Methode zu deklarieren, " +
                "allerdings den Namen der Variable/Methode vergisst.";
        solutionMessage = "Lösche den Variablen- oder Methodennamen in einer entsprechenden Lücke.";
        taskType = "IdentifierExpectedErrorTask";
    }

    /**
     * Introduces a gap in the code by replacing a valid identifier with a placeholder.
     *
     * @param code   The code snippet to modify by introducing a gap.
     * @param random A random number generator for selecting positions.
     * @return true if a gap was successfully created, false otherwise.
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        if (random.nextBoolean()){
            return createSolutionGapForAttributes(code, random);
        } else {
            return createSolutionGapForMethods(code, random);
        }
    }

    /**
     * Introduces a gap in the code by replacing a valid attribute with a placeholder.
     *
     * @param code   The code snippet to modify by introducing a gap.
     * @param random A random number generator for selecting positions.
     * @return true if a gap was successfully created, false otherwise.
     */
    public boolean createSolutionGapForAttributes(StringBuilder code, Random random) {
        List<String> identifiers = generatedAttributes;
        if (identifiers.isEmpty()) {
            logger.warning("No valid identifiers available for creating gaps.");
            return false;
        }

        String identifier = identifiers.get(random.nextInt(identifiers.size()));
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), identifier);

        if (positions.isEmpty()) {
            logger.warning("No positions found for identifier: " + identifier);
            return false;
        }

        int position = positions.get(0);
        code.replace(position, position + identifier.length(), "[[" + identifier + "]]");
        logger.info("Solution gap created at position: " + position);
        return true;
    }

    /**
     * Introduces a gap in the code by replacing a valid method with a placeholder.
     *
     * @param code   The code snippet to modify by introducing a gap.
     * @param random A random number generator for selecting positions.
     * @return true if a gap was successfully created, false otherwise.
     */
    public boolean createSolutionGapForMethods(StringBuilder code, Random random){
        Collections.shuffle(generatedMethods);
        for (String method : generatedMethods){
            List<Integer> positions = findAllOccurrencesOfWords(code.toString(), method);
            int position = positions.get(0);
            int start = Math.max(0, position - 10);
            String precedingText = code.substring(start, position).toLowerCase();
            if (!precedingText.contains("string")) {
                code.replace(position, position + method.length(), "[[" + method + "]]");
                logger.info("Solution gap created at position: " + position);
                return true;
            }
        }
        return false;
    }
}