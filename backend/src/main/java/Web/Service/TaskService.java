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
    public Task generateRandomTask(String category) {
        Random random = new Random();
        System.out.println("Category: " + category);

        List<String> weightedTaskTypes;
        switch (category) {
            case "type":
                System.out.println("Type");
                weightedTaskTypes = new ArrayList<>(taskTypeProvider.getTypeErrorTasks());
                break;
            case "syntax":
                System.out.println("Syntax");
                weightedTaskTypes = new ArrayList<>(taskTypeProvider.getSyntaxErrorTasks());
                break;
            case "declaration":
                System.out.println("Declaration");
                weightedTaskTypes = new ArrayList<>(taskTypeProvider.getDeclarationErrorTasks());
                break;
            case "full":
                System.out.println("Full");
                weightedTaskTypes = new ArrayList<>(taskTypeProvider.getAllTaskTypes());
                break;
            default:
                System.out.println("Default");
                weightedTaskTypes = new ArrayList<>(taskTypeProvider.getAllTaskTypes());
        }

        trackingService.getTaskPerformance().forEach((taskType, weight) -> {
            if (weightedTaskTypes.contains(taskType)) {
                for (int i = 1; i < weight; i++) {
                    weightedTaskTypes.add(taskType);
                }
            }
        });

        int averageScore = taskTypeProvider.getAllTaskTypes().size() * trackingService.getDEFAULT_SCORE();
        int actualScore = weightedTaskTypes.size();
        int gapCount = random.nextInt(3) + 5;

        if (actualScore < averageScore - 10) {
            gapCount = random.nextInt(4) + 6;
        } else if (actualScore > averageScore + 10) {
            gapCount = random.nextInt(2) + 3;
        }

        System.out.println("Weighted Task Types: " + weightedTaskTypes);
        String selectedTaskType = weightedTaskTypes.get(random.nextInt(weightedTaskTypes.size()));
        return TaskFactory.createTask(selectedTaskType, gapCount);
    }

}