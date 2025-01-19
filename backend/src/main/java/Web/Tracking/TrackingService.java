package Web.Tracking;

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

    private final List<UserInteraction> userInteractions;
    private final HashMap<String, Integer> taskPerformance;
    private String csvFilePath;

    /**
     * Constructor initializes tracking data structures.
     */
    public TrackingService() {
        this.userInteractions = new ArrayList<>();
        this.taskPerformance = new HashMap<>();
        this.csvFilePath = Paths.get(System.getProperty("user.dir"), "user_interactions.csv").toString();
        initializeCSV();
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

        taskPerformance.putIfAbsent(interaction.getTaskType(), 0);

        if (interaction.getAttemptCount() == 1) {
            taskPerformance.put(interaction.getTaskType(), taskPerformance.get(interaction.getTaskType()) + 1);
        } else {
            taskPerformance.put(interaction.getTaskType(), taskPerformance.get(interaction.getTaskType()) - 1);
        }

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
     * Adjusts the likelihood of generating specific tasks based on user performance.
     *
     * @return A map of task types and their adjusted weights.
     */
    public HashMap<String, Integer> getTaskGenerationWeights() {
        HashMap<String, Integer> weights = new HashMap<>();

        for (String taskType : taskPerformance.keySet()) {
            int performanceScore = taskPerformance.get(taskType);

            weights.put(taskType, Math.max(1, 10 - performanceScore));
        }

        return weights;
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
}