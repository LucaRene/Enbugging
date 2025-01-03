package Task;

import Context.ContextStrategy;
import Context.PersonContext;
import Context.PlayerContext;
import Context.VehicleContext;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Factory class for creating Task instances with a randomly selected context.
 * Provides flexibility to generate tasks with different contextual attributes and methods.
 */
public class TaskFactory {

    private static final Random RANDOM = new Random();
    private static final List<ContextStrategy> CONTEXTS = Arrays.asList(
            new PersonContext(),
            new PlayerContext(),
            new VehicleContext()
    );

    /**
     * Creates a Task with a randomly selected context from the available contexts.
     * This allows for task generation with various contextual attributes and methods.
     *
     * @param taskType Ignored parameter for the task type (can be used for future expansion).
     * @return A new Task instance with a randomly selected context.
     */
    public static Task createTask(String taskType, int gapCount) {

        ContextStrategy context = CONTEXTS.get(RANDOM.nextInt(CONTEXTS.size()));

        switch (taskType) {
            case "SemicolonErrorTask":
                return new SemicolonErrorTask(context, gapCount);
            case "UnclosedStringErrorTask":
                return new UnclosedStringErrorTask(context, gapCount);
            case "ReachedEndOfFileErrorTask":
                return new ReachedEndOfFileErrorTask(context, gapCount);
            case "CannotFindSymbolErrorTask":
                return new CannotFindSymbolErrorTask(context, gapCount);
            case "ReturnTypeRequiredErrorTask":
                return new ReturnTypeRequiredErrorTask(context, gapCount);
            default:
                return null;
        }
    }
}
