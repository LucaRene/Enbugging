package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a potential "unclosed string literal" error.
 */
public class UnclosedStringErrorTask extends Task {

    /**
     * Constructs a new UnclosedStringErrorTask with the specified context.
     *
     * @param context the context used to generate attributes and methods
     */
    public UnclosedStringErrorTask(ContextStrategy context) {
        super(context);

        while (!getTaskCodeWithoutGaps().contains("String")){
            taskCodeWithoutGaps.setLength(0);
            generateTaskCode();
        }

        expectedErrorMessage = "unclosed string literal";
        createGapsInCode();
    }

    /**
     * Creates a gap in the code by replacing a string literal with a gap.
     *
     * @param code   the code snippet to introduce the gap
     * @param random the random number generator
     */
    @Override
    protected void createSolutionGap(StringBuilder code, Random random) {
        List<Integer> stringPositions = findAllOccurrencesOfWords(code.toString(), "\"");
        if (stringPositions.size() >= 2) {
            int startPosition = stringPositions.get(random.nextInt(stringPositions.size()));
            code.replace(startPosition, startPosition + 1, "[\"]");
        }
    }
}