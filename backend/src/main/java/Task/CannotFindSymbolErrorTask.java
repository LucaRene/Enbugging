package Task;

import Context.ContextStrategy;

import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a "cannot find symbol" error.
 */
public class CannotFindSymbolErrorTask extends Task {

    /**
     * Constructs a new CannotFindSymbolErrorTask with the specified context.
     *
     * @param context  the context used to generate attributes and methods
     * @param gapCount the number of gaps to create in the task
     */
    public CannotFindSymbolErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "cannot find symbol";
    }

    /**
     * Creates a gap in the code by replacing a variable name with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        if (generatedAttributes.isEmpty()) {
            return false;
        }

        String attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), attribute);
        List<Integer> wrongPositions = findAllOccurrencesOfWords(code.toString(), attribute + "Neu");
        while (positions.size() < 2) {
            attribute = generatedAttributes.get(random.nextInt(generatedAttributes.size()));
            positions = findAllOccurrencesOfWords(code.toString(), attribute);
            wrongPositions = findAllOccurrencesOfWords(code.toString(), attribute + "Neu");
        }

        for (Integer i: wrongPositions) {
            positions.remove(i);
        }

        int position = positions.get(random.nextInt(positions.size()));
        code.replace(position, position + attribute.length(), "[[" + attribute + "]]");
        return true;
    }
}