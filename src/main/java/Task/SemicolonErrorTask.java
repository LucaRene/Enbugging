package Task;

import Context.ContextStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A specialized Task that generates a Task with Gaps containing a missing semicolon.
 */
public class SemicolonErrorTask extends Task {

    /**
     * Constructs a new SemicolonErrorTask with the specified context.
     *
     * @param context the context used to generate attributes and methods
     */
    public SemicolonErrorTask(ContextStrategy context) {
        super(context);
        expectedErrorMessage = "';' expected";
        createGapsInCode();
    }

    /**
     * Creates gaps in the generated code by replacing selected keywords or symbols with gaps.
     */
    protected void createGapsInCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(taskCodeWithoutGaps);
        List<String> words = new ArrayList<>(Arrays.stream(code.toString().split("(?<=;)|(?=;)|(?<=\\()|(?=\\()|(?<=\\))|(?=\\))|\\s+"))
                .filter(word -> !word.isEmpty())
                .toList());

        createSemicolonGap(code, random);

        for (int i = 0; i < 4; i++) {

            int index = random.nextInt(words.size());
            String gap = words.get(index);

            while (gap.isEmpty() || gap.isBlank()) {
                index = random.nextInt(words.size());
                gap = words.get(index);
            }

            List<Integer> positions = findAllOccurrencesOfWords(code.toString(), gap);
            int position = positions.get(random.nextInt(positions.size()));
            code.replace(position, position + gap.length(), "[" + gap + "]");

            words.remove(index);
        }

        taskCodeWithGaps.setLength(0);
        taskCodeWithGaps.append(code);
    }

    /**
     * Creates a gap in the code by replacing a semicolon with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    private void createSemicolonGap(StringBuilder code, Random random) {
        List<Integer> positions = findAllOccurrencesOfWords(code.toString(), ";");
        if (!positions.isEmpty()) {
            int position = positions.get(random.nextInt(positions.size()));
            code.replace(position, position + 1, "[;]");
        }
    }
}