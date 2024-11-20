package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a missing semicolon.
 */
public class ReachedEndOfFileErrorTask extends Task {

    /**
     * Constructs a new SemicolonErrorTask with the specified context.
     *
     * @param context the context used to generate attributes and methods
     */
    public ReachedEndOfFileErrorTask(ContextStrategy context) {
        super(context);
        expectedErrorMessage = "reached end of file while parsing";
        createGapsInCode();
    }

    /**
     * Creates a gap in the code by replacing a semicolon with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    @Override
    protected void createSolutionGap(StringBuilder code, Random random) {
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), "}");
        if (!positions.isEmpty()) {
            int position = positions.get(positions.size()-1);
            code.replace(position, position + 1, "[}]");
        }
    }
}