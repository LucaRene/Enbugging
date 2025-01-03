package Web.Service;

import Task.Task;
import Task.TaskFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service to handle task generation logic.
 */
@Service
public class TaskService {

    /**
     * Generates a random task.
     *
     * @return a Task instance
     */
    public Task generateRandomTask() {
        Random random = new Random();
        List<String> possibleErrors = new ArrayList<String>();
        possibleErrors.add("SemicolonErrorTask");
        possibleErrors.add("UnclosedStringErrorTask");
        possibleErrors.add("ReachedEndOfFileErrorTask");
        possibleErrors.add("CannotFindSymbolErrorTask");
        possibleErrors.add("ReturnTypeRequiredErrorTask");

        return TaskFactory.createTask(possibleErrors.get(random.nextInt(possibleErrors.size())), 6);
        //return TaskFactory.createTask("ReturnTypeRequiredErrorTask", 8);
    }
}