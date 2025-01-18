package Task;

import Context.ContextStrategy;

import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a
 * "missing return statement" error.
 */
public class MissingReturnStatementErrorTask extends Task {

    /**
     * Constructs a new MissingReturnStatementErrorTask with the specified context.
     *
     * @param context  the context used to generate attributes and methods
     * @param gapCount the number of gaps to create in the task
     */
    public MissingReturnStatementErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "missing return statement";
    }

    /**
     * Creates a gap in the code by replacing the "void" return type of a method
     * with a placeholder, creating an opportunity for a missing return statement error.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     * @return true if a gap was created successfully, false otherwise
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), "void");
        if (positions.isEmpty()) {
            logger.warning("'void' keyword not found in the code.");
            return false;
        }

        int position = positions.get(random.nextInt(positions.size()));
        code.replace(position, position + "void".length(), "[[void]]");
        logger.info("Solution gap created: 'void' replaced with '[[void]]'.");
        return true;
    }
}