package Task;

import Context.ContextStrategy;

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
    public ReachedEndOfFileErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "reached end of file while parsing";
        hintMessage = "Der Inhalt einer Klasse in Java befindet sich immer zwischen zwei geschweiften " +
                "Klammern ({, }). Wenn diese Fehlermeldung auftritt, fehlt mindestens eine schließende Klammer (}).";
        solutionMessage = "Entferne eine schließende geschweifte Klammer (}) am Ende der Klasse.";
        taskType = "ReachedEndOfFileErrorTask";
    }

    /**
     * Creates a gap in the code by replacing a semicolon with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), "}");
        if (!positions.isEmpty()) {
            int position = positions.get(positions.size()-1);
            code.replace(position, position + 1, "[[}]]");
        }
        return true;
    }
}