package Task;

import Context.ContextStrategy;

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
    public UnclosedStringErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "unclosed string literal";
        hintMessage = "Diese Fehlermeldung tritt auf, wenn eine Zeichenkette niemals geschlossen wird. Ein String" +
                " muss mit einem doppelten Anführungszeichen (\") beginnen und enden.";
        solutionMessage = "Entferne eines der Anführungszeichen (\").";
        taskType = "UnclosedStringErrorTask";

        int stringAttributeCount = countStringAttributes();
        while (stringAttributeCount < 1) {
            resetTask();
            generateTaskCode();
            stringAttributeCount = countStringAttributes();
        }

        createGapsInCode();
    }

    public int countStringAttributes() {
        int stringAttributeCount = 0;
        for (String attribute : generatedAttributes) {
            if (getJavaType(context.getRandomValueForAttribute(attribute)).equals("String")) {
                stringAttributeCount++;
            }
        }
        return stringAttributeCount;
    }

    /**
     * Creates a gap in the code by replacing a string literal with a gap.
     *
     * @param code   the code snippet to introduce the gap
     * @param random the random number generator
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        List<Integer> stringPositions = findAllOccurrencesOfWords(code.toString(), "\"");
        if (stringPositions.size() >= 2) {
            int startPosition = stringPositions.get(random.nextInt(stringPositions.size()));
            code.replace(startPosition, startPosition + 1, "[[\"]]");
        }
        return true;
    }
}