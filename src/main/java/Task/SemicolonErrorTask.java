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

        int position;
        List<Integer> positions;
        for (int i = 0; i < 4; i++) {

            int index = random.nextInt(words.size());
            String gap = words.get(index);

            while (gap.isEmpty() || gap.isBlank()) {
                index = random.nextInt(words.size());
                gap = words.get(index);
            }

            positions = findAllOccurrences(code.toString(), gap);
            position = positions.get(random.nextInt(positions.size()));
            code.replace(position, position + gap.length(), "[" + gap + "]");

            words.remove(index);
        }

        taskCodeWithGaps.append(code);
    }

    /**
     * Creates a gap in the code by replacing a semicolon with a gap.
     *
     * @param code   the code snippet to modify
     * @param random the random number generator
     */
    private void createSemicolonGap(StringBuilder code, Random random) {
        List<Integer> positions = findAllOccurrences(code.toString(), ";");
        if (!positions.isEmpty()) {
            int position = positions.get(random.nextInt(positions.size()));
            code.replace(position, position + 1, "[;]");
        }
    }

    /**
     * Finds all occurrences of a substring within a string.
     *
     * @param text the text to search for the substring
     * @param sub  the substring to find within the text
     * @return a list of positions where the substring occurs in the text
     */
    private List<Integer> findAllOccurrences(String text, String sub) {
        List<Integer> positions = new ArrayList<>();
        int index = text.indexOf(sub);
        while (index >= 0) {
            positions.add(index);
            index = text.indexOf(sub, index + sub.length());
        }
        return positions;
    }
}