package Task;

import Context.ContextStrategy;

import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a
 * "missing return value" error.
 */
public class MissingReturnValueErrorTask extends Task {

    /**
     * Constructs a new MissingReturnValueErrorTask with the specified context.
     *
     * @param context  the context used to generate attributes and methods
     * @param gapCount the number of gaps to create in the task
     */
    public MissingReturnValueErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "missing return value";
        hintMessage = "Diese Fehlermeldung tritt auf, wenn bei einer Methode mit Rückgabewert vergessen wird, einen Wert nach dem return anzugeben.";
        solutionMessage = "Lösche einen Variablennamen nach einem return.";
        taskType = "MissingReturnValueErrorTask";
    }

    /**
     * Creates a gap in the code by adding a space after the return keyword.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        if (generatedMethods.isEmpty()) {
            logger.warning("No methods available for gap creation.");
            return false;
        }

        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), "return");
        if (positions.isEmpty()) {
            logger.warning("'return' keyword not found in the code.");
            return false;
        }

        int position = positions.get(random.nextInt(positions.size()));
        int start = position + 7;
        int end = start;

        while (end < code.length() && Character.isLetterOrDigit(code.charAt(end))) {
            end++;
        }

        if (start == end) {
            logger.warning("No valid identifier found after 'return'.");
            return false;
        }

        String identifier = code.substring(start, end);
        code.replace(start, end, "[[" + identifier + "]]");
        logger.info("Solution gap created: 'return " + identifier + "' replaced with 'return [[identifier]]'.");
        return true;
    }

}
