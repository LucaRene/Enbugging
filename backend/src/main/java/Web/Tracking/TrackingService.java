package Web.Tracking;

import Task.TaskTypeProvider;
import Web.Service.TaskService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Service class to handle tracking of user interactions.
 */
@Service
public class TrackingService {

    private final int DEFAULT_SCORE = 5;
    private final List<UserInteraction> userInteractions;
    private final HashMap<String, Integer> taskPerformance;
    private final String csvFilePath;

    /**
     * Constructor initializes tracking data structures.
     */
    public TrackingService(List<String> taskTypes) {
        this.userInteractions = new ArrayList<>();
        this.taskPerformance = new HashMap<>();
        this.csvFilePath = Paths.get(System.getProperty("user.dir"), "user_interactions.csv").toString();
        TaskTypeProvider taskTypeProvider = new TaskTypeProvider();
        initializeCSV();
        initializeTaskPerformance(taskTypeProvider.getTaskTypes());
    }

    /**
     * Initializes the task performance map with default scores.
     */
    private void initializeTaskPerformance(List<String> taskTypes) {
        for (String taskType : taskTypes) {
            taskPerformance.put(taskType, DEFAULT_SCORE);
        }
    }

    /**
     * Initializes the CSV file with headers.
     */
    private void initializeCSV() {
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            writer.write("TaskType,StartTime,EndTime,Attempts,SolvedCorrectly\n");
        } catch (IOException e) {
            System.err.println("Failed to initialize CSV file: " + e.getMessage());
        }
    }

    /**
     * Records a user interaction.
     *
     * @param interaction The user interaction to record.
     */
    public void recordInteraction(UserInteraction interaction) {
        userInteractions.add(interaction);
        rewriteCSV();
    }

    /**
     * Rewrites the entire CSV file with the current list of user interactions.
     */
    public void rewriteCSV() {
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            writer.write("TaskType,StartTime,EndTime,Attempts,SolvedCorrectly\n");
            for (UserInteraction interaction : userInteractions) {
                writer.write(formatInteraction(interaction));
            }
        } catch (IOException e) {
            System.err.println("Failed to rewrite CSV file: " + e.getMessage());
        }
    }

    /**
     * Formats a user interaction as a CSV row.
     *
     * @param interaction The user interaction to format.
     * @return The formatted CSV row.
     */
    private String formatInteraction(UserInteraction interaction) {
        return String.format("%s,%s,%s,%d,%b\n",
                interaction.getTaskType(),
                interaction.getStartTime(),
                interaction.getEndTime(),
                interaction.getAttemptCount(),
                interaction.isSolvedCorrectly());
    }


    /**
     * Increases the performance score for a specific task type.
     *
     * @param taskType The task type to increase the performance score for.
     */
    public void increaseTaskPerformance(String taskType) {
        taskPerformance.putIfAbsent(taskType, 5);
        if (taskPerformance.get(taskType) < 10){
            taskPerformance.put(taskType, taskPerformance.get(taskType) + 1);
        }
    }

    /**
     * Decreases the performance score for a specific task type.
     *
     * @param taskType The task type to decrease the performance score for.
     */
    public void decreaseTaskPerformance(String taskType) {
        taskPerformance.putIfAbsent(taskType, 5);
        if (taskPerformance.get(taskType) > 1) {
            taskPerformance.put(taskType, taskPerformance.get(taskType) - 1);
        }
    }

    /**
     * Clears all tracking data.
     * Useful for resetting the system between sessions.
     */
    public void clearData() {
        userInteractions.clear();
        taskPerformance.clear();
    }

    /**
     * Returns the list of user interactions.
     *
     * @return List of user interactions.
     */
    public List<UserInteraction> getUserInteractions() {
        return userInteractions;
    }

    /**
     * Returns the task performance map.
     *
     * @return The task performance map.
     */
    public HashMap<String, Integer> getTaskPerformance() {
        return taskPerformance;
    }

    /**
     * Returns the default score for tasks.
     *
     * @return The default score for tasks.
     */
    public int getDEFAULT_SCORE() {
        return DEFAULT_SCORE;
    }
}