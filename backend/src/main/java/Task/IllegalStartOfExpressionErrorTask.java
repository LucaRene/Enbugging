package Task;

import Context.ContextStrategy;

import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing an "illegal start of expression" error.
 */
public class IllegalStartOfExpressionErrorTask extends Task {

    /**
     * Constructs a new IllegalStartOfExpressionErrorTask with the specified context.
     *
     * @param context  the context used to generate attributes and methods
     * @param gapCount the number of gaps to create in the task
     */
    public IllegalStartOfExpressionErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "illegal start of expression";
    }

    /**
     * Generates a ransom class with the specified context and gap count.
     * The class must contain at least one variable and two methods.
     */
    @Override
    public boolean generateTaskCode() {
        logger.info("Generating task code...");
        Random random = new Random();

        createClassDeclaration();

        int variableCount = random.nextInt(3) + 1;
        logger.info("Number of variables to generate: " + variableCount);
        for (int i = 0; i < variableCount; i++) {
            createVariable();
        }

        int methodCount = 2;
        logger.info("Number of methods to generate: " + methodCount);

        boolean getterProhibited = false;
        boolean setterProhibited = false;
        for (int i = 0; i < methodCount; i++) {
            if (getterProhibited && setterProhibited) {
                break;
            } else if (getterProhibited) {
                setterProhibited = !createSetter();
                if (setterProhibited) {
                    i--;
                }
            } else if (setterProhibited) {
                getterProhibited = !createGetter();
                if (getterProhibited) {
                    i--;
                }
            } else {
                if (random.nextBoolean()) {
                    getterProhibited = !createGetter();
                    if (getterProhibited) {
                        i--;
                    }
                } else {
                    setterProhibited = !createSetter();
                    if (setterProhibited) {
                        i--;
                    }
                }
            }
        }

        closeClass();
        logger.info("Task code generation complete.");
        return true;
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

        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), "}");
        int position = positions.get(0);
        code.replace(position, position + 1, "[}]");
        return true;
    }
}
