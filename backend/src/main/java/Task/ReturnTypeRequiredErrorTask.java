package Task;

import Context.ContextStrategy;

import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing an
 * "invalid method declaration; return type required" error.
 */
public class ReturnTypeRequiredErrorTask extends Task {

    /**
     * Constructs a new CannotFindSymbolErrorTask with the specified context.
     *
     * @param context  the context used to generate attributes and methods
     * @param gapCount the number of gaps to create in the task
     */
    public ReturnTypeRequiredErrorTask(ContextStrategy context, int gapCount) {
        super(context, gapCount);
        expectedErrorMessage = "invalid method declaration; \nreturn type required";
    }

    /**
     * Creates a gap in the code by creating a gap around a return type.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    @Override
    protected boolean createSolutionGap(StringBuilder code, Random random) {
        if (generatedAttributes.isEmpty()) {
            return false;
        }

        boolean returnTypeFound = false;
        boolean voidFound = false;
        for (String method: generatedMethods) {
            if (method.contains("get")){
                returnTypeFound = true;
            } else if (method.contains("set")) {
                voidFound = true;
            }
        }

        if (!returnTypeFound) {
            replaceVoidWithGap(code, random);
        } else if (!voidFound) {
            replaceReturnTypeWithGap(code, random);
        } else {
            if (random.nextBoolean()) {
                replaceVoidWithGap(code, random);
            } else {
                replaceReturnTypeWithGap(code, random);
            }
        }
        return true;
    }

    /**
     * Creates a gap in the code by replacing a void with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    public void replaceVoidWithGap(StringBuilder code, Random random) {
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), "void");
        int position = positions.get(random.nextInt(positions.size()));
        code.replace(position, position + "void".length(), "[[void]]");
    }

    /**
     * Creates a gap in the code by replacing a return type with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    public void replaceReturnTypeWithGap(StringBuilder code, Random random) {
        generatedMethods.removeIf(method -> method.contains("set"));

        String toModify = generatedMethods.get(random.nextInt(generatedMethods.size()));
        String attribute = toModify.substring(3);
        attribute = attribute.replace(attribute.charAt(0), Character.toLowerCase(attribute.charAt(0)));
        String returnType = getJavaType(context.getRandomValueForAttribute(attribute));

        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), returnType + " get" + capitalize(attribute));
        int position = positions.get(random.nextInt(positions.size()));
        code.replace(position, position + returnType.length(), "[[" + returnType + "]]");
    }
}
