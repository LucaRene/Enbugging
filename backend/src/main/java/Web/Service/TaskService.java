package Web.Service;

import Task.Task;
import Task.TaskFactory;
import Task.TaskTypeProvider;
import Web.Tracking.TrackingService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service to handle task generation logic.
 */
@Service
public class TaskService {

    final TrackingService trackingService;
    final TaskTypeProvider taskTypeProvider;

    /**
     * Constructor to initialize the task service.
     *
     * @param trackingService The tracking service to use
     */
    public TaskService(TrackingService trackingService) {
        this.trackingService = trackingService;
        taskTypeProvider = new TaskTypeProvider();
    }

    /**
     * Generates a random task based on the user's previous interactions.
     *
     * @return A randomly generated task
     */
    public Task generateRandomTask() {
        Random random = new Random();

        List<String> weightedTaskTypes = new ArrayList<>(taskTypeProvider.getTaskTypes());
        for (HashMap.Entry<String, Integer> entry : trackingService.getTaskPerformance().entrySet()) {
            String taskType = entry.getKey();
            int weight = entry.getValue();
            for (int i = 1; i < weight; i++) {
                weightedTaskTypes.add(taskType);
            }
        }

        int averageScore = taskTypeProvider.getTaskTypes().size() * trackingService.getDEFAULT_SCORE();
        int actualScore = weightedTaskTypes.size();
        int gapCount = random.nextInt(3) + 5;

        if (actualScore < averageScore-10) {
            gapCount = random.nextInt(4) + 6;
        } else if (actualScore > averageScore+10) {
            gapCount = random.nextInt(2) + 3;
        }

        String selectedTaskType = weightedTaskTypes.get(random.nextInt(weightedTaskTypes.size()));
        return TaskFactory.createTask(selectedTaskType, gapCount);
    }

}