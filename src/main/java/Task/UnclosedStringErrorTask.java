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
     * Creates gaps in the generated code by introducing gaps that may lead to an "unclosed string literal" error.
     */
    protected void createGapsInCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(taskCodeWithoutGaps);
        List<String> words = new ArrayList<>(Arrays.stream(code.toString().split("(?<=;)|(?=;)|(?<=\\()|(?=\\()|(?<=\\))|(?=\\))|\\s+"))
                .filter(word -> !word.isEmpty() && !word.isBlank())
                .toList());

        createStringLiteralGap(code, random);

        for (int i = 0; i < 4; i++) {
            if (words.isEmpty()) break; // Prevents IndexOutOfBoundsException

            int index = random.nextInt(words.size());
            String gap = words.get(index);

            List<Integer> positions = findAllOccurrencesOfWords(code.toString(), gap);
            if (!positions.isEmpty()) {
                int position = positions.get(random.nextInt(positions.size()));
                code.replace(position, position + gap.length(), "[" + gap + "]");
            }

            words.remove(index);
        }

        taskCodeWithGaps.setLength(0);
        taskCodeWithGaps.append(code);
    }

    /**
     * Creates a gap in the code by replacing a string literal with a gap.
     *
     * @param code   the code snippet to introduce the gap
     * @param random the random number generator
     */
    private void createStringLiteralGap(StringBuilder code, Random random) {
        List<Integer> stringPositions = findAllOccurrencesOfWords(code.toString(), "\"");
        if (stringPositions.size() >= 2) {
            int startPosition = stringPositions.get(random.nextInt(stringPositions.size()));
            code.replace(startPosition, startPosition + 1, "[\"]");
        }
    }
}