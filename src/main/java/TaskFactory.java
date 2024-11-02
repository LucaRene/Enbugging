import Context.PersonContext;
import Context.PlayerContext;
import Context.VehicleContext;
import Task.Task;
import Context.ContextStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TaskFactory {

    private static final Random RANDOM = new Random();
    private static final List<ContextStrategy> CONTEXTS = Arrays.asList(
            new PersonContext(),
            new PlayerContext(),
            new VehicleContext()
    );

    public static Task createTask(String taskType) {
        ContextStrategy context = CONTEXTS.get(RANDOM.nextInt(CONTEXTS.size()));
        return new Task(context);
    }
}
