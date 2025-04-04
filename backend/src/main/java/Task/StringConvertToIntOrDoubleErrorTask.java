package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing an
 * "incompatible types: int cannot be converted to java.lang.String" error.
 */
public class StringConvertToIntOrDoubleErrorTask extends Task {

    private static final String STRING_KEYWORD = "String ";
    private static final String INVALID_STRING_KEYWORD = "(String";

    /**
     * Constructs a new IntConvertToStringErrorTask and ensures that the generated
     * code contains at least one 'String' declaration to ensure the task is solvable.
     *
     * @param context  The context strategy for generating attributes and methods.
     * @param gapCount The number of gaps to introduce in the code.
     */
    public StringConvertToIntOrDoubleErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);

        Random random = new Random();
        if (random.nextBoolean()) {
            expectedErrorMessage = "incompatible types: \njava.lang.String cannot be converted to int";
            hintMessage = "Bei dieser Fehlermeldung, passen die Datentypen nicht zusammen. Der Nutzer hat hier " +
                    "versucht einen String-wert in einer Variable, die int-Werte speichert, zu speichern.";
            solutionMessage = "Ändere bei einer String-Variable den Datentyp bei der Deklaration zu int.";
            taskType = "StringConvertToIntOrDoubleErrorTask";
        } else {
            expectedErrorMessage = "incompatible types: \njava.lang.String cannot be converted to double";
            hintMessage = "Bei dieser Fehlermeldung, passen die Datentypen nicht zusammen. Der Nutzer hat hier " +
                    "versucht einen String-wert in einer Variable, die double-Werte speichert, zu speichern.";
            solutionMessage = "Ändere bei einer String-Variable den Datentyp bei der Deklaration zu double.";
            taskType = "StringConvertToIntOrDoubleErrorTask";
        }

        while (!getTaskCodeWithoutGaps().contains(STRING_KEYWORD)) {
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

        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), STRING_KEYWORD);
        List<Integer> wrongPositions = adjustWrongPositions(findAllOccurrencesOfWords(code.toString(), INVALID_STRING_KEYWORD));

        positions.removeAll(wrongPositions);

        if (positions.isEmpty()) {
            logger.warning("No valid 'String' positions available after filtering invalid positions.");
            return false;
        }

        int position = positions.get(random.nextInt(positions.size()));
        code.replace(position, position + STRING_KEYWORD.length() - 1, "[[" + STRING_KEYWORD.trim() + "]]");
        logger.info("Solution gap created at position: " + position);
        return true;
    }

    /**
     * Adjusts positions of invalid 'String' occurrences (e.g., "(String") to target the correct keyword.
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