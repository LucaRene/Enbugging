package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a
 * "incompatible types: int cannot be converted to java.lang.String" error.
 */
public class IntConvertToStringErrorTask extends Task {

    private static final String INT_KEYWORD = "int ";
    private static final String INVALID_INT_KEYWORD = "(int";

    /**
     * Constructs a new IntConvertToStringErrorTask and ensures that the generated
     * code contains at least one 'int' declaration to allow for gap creation.
     *
     * @param context  The context strategy for generating attributes and methods.
     * @param gapCount The number of gaps to introduce in the code.
     */
    public IntConvertToStringErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "incompatible types: int cannot be converted to java.lang.String";

        while (!getTaskCodeWithoutGaps().contains("int")) {
            resetTask();
            generateTaskCode();
        }

        createGapsInCode();
    }

    /**
     * Introduces a gap in the code by replacing a valid 'int' keyword with a placeholder.
     * Ensures that 'int' within parameter declarations (e.g., "(int") is not replaced.
     *
     * @param code   The code snippet to modify by introducing a gap.
     * @param random A random number generator for selecting positions.
     * @return true if a gap was successfully created, false otherwise.
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        if (generatedMethods.isEmpty()) {
            logger.warning("No generated methods available. Gap creation aborted.");
            return false;
        }

        // Find valid and invalid positions
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), INT_KEYWORD);
        List<Integer> wrongPositions = adjustWrongPositions(findAllOccurrencesOfWords(code.toString(), INVALID_INT_KEYWORD));

        positions.removeAll(wrongPositions);

        if (positions.isEmpty()) {
            logger.warning("No valid 'int' positions available after filtering invalid positions.");
            return false;
        }

        int position = positions.get(random.nextInt(positions.size()));
        code.replace(position, position + 3, "[[int]]");
        logger.info("Solution gap created at position: " + position);
        return true;
    }

    /**
     * Adjusts positions of invalid 'int' occurrences (e.g., "(int") to target the correct keyword.
     *
     * @param positions The list of positions to adjust.
     * @return A list of adjusted positions.
     */
    private List<Integer> adjustWrongPositions(List<Integer> positions) {
        List<Integer> adjusted = new ArrayList<>();
        for (int pos : positions) {
            adjusted.add(pos + 1);
        }
        return adjusted;
    }
}