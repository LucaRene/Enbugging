package Web.Service;

import Task.Task;
import Task.TaskFactory;
import Task.TaskTypeProvider;
import Web.Tracking.TrackingService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Service responsible for generating random programming tasks based on specified categories,
 * predefined task configurations, and user performance tracking data.
 */
@Service
public class TaskService {

    private final TrackingService trackingService;
    private final TaskTypeProvider taskTypeProvider;
    private final List<String> predefinedTaskTypes;

    /**
     * Constructs a new TaskService instance and initializes necessary components.
     *
     * @param trackingService the tracking service used to monitor user interactions and performance
     */
    public TaskService(TrackingService trackingService) {
        this.trackingService = trackingService;
        this.taskTypeProvider = new TaskTypeProvider();
        this.predefinedTaskTypes = loadPredefinedTasks();
    }

    /**
     * Loads a list of predefined task types from a configuration file named "config.txt".
     * The file should be located in the same directory as the application's JAR file.
     *
     * @return a list containing predefined task types; if the file does not exist or an error occurs, returns an empty list
     */
    private List<String> loadPredefinedTasks() {
        File file = new File("config.txt");
        if (!file.exists()) {
            return Collections.emptyList();
        }

        try {
            return Files.readAllLines(file.toPath())
                    .stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .toList();
        } catch (IOException e) {
            System.err.println("Could not read config.txt: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Generates and returns a random task based on the provided category, predefined tasks from configuration,
     * and the user's previous interactions to dynamically adjust task selection.
     *
     * @param category the category of tasks requested ("type", "syntax", "declaration", or "full")
     * @return a randomly generated task according to the selected criteria
     */
    public Task generateRandomTask(String category) {
        Random random = new Random();
        System.out.println("Category: " + category);

        List<String> weightedTaskTypes;

        if (!predefinedTaskTypes.isEmpty()) {
            weightedTaskTypes = new ArrayList<>(predefinedTaskTypes);
        } else {
            switch (category.toLowerCase()) {
                case "type":
                    weightedTaskTypes = new ArrayList<>(taskTypeProvider.getTypeErrorTasks());
                    break;
                case "syntax":
                    weightedTaskTypes = new ArrayList<>(taskTypeProvider.getSyntaxErrorTasks());
                    break;
                case "declaration":
                    weightedTaskTypes = new ArrayList<>(taskTypeProvider.getDeclarationErrorTasks());
                    break;
                case "full":
                    weightedTaskTypes = new ArrayList<>(taskTypeProvider.getAllTaskTypes());
                    break;
                default:
                    weightedTaskTypes = new ArrayList<>(taskTypeProvider.getAllTaskTypes());
            }
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

    /**
     * Checks whether predefined task types from the configuration file have been loaded successfully.
     *
     * @return {@code true} if predefined tasks exist; {@code false} otherwise
     */
    public boolean hasPredefinedTasks() {
        return !predefinedTaskTypes.isEmpty();
    }
}