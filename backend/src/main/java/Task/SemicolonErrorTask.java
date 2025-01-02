package Task;

import Context.ContextStrategy;

import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a missing semicolon.
 */
public class SemicolonErrorTask extends Task {

    /**
     * Constructs a new SemicolonErrorTask with the specified context.
     *
     * @param context the context used to generate attributes and methods
     */
    public SemicolonErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "';' expected";
    }

    /**
     * Creates a gap in the code by replacing a semicolon with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    @Override
    protected void createSolutionGap(StringBuilder code, Random random) {
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), ";");
        if (!positions.isEmpty()) {
            int position = positions.get(random.nextInt(positions.size()));
            code.replace(position, position + 1, "[;]");
        }
    }
}