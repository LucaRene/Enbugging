package Web.Tracking;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Represents a single user interaction for a task.
 */
public class UserInteraction {

    private String taskId;
    private String taskType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int attemptCount;
    private boolean solvedCorrectly;

    /**
     * Constructor to initialize a user interaction.
     *
     * @param taskType        Type of the task
     */
    public UserInteraction(String taskType) {
        this.taskId = generateTaskId();
        this.taskType = taskType;
        this.startTime = LocalDateTime.now();
        this.attemptCount = 0;
        this.solvedCorrectly = false;
    }

    /**
     * Generates a unique task ID based on the current time.
     *
     * @return A unique task ID
     */
    private String generateTaskId() {
        return "task-" + LocalDateTime.now();
    }

    /**
     * Marks the task as completed and records the end time.
     *
     * @param endTime           End time of the task
     * @param solvedCorrectly   Whether the task was solved correctly
     */
    public void completeTask(LocalDateTime endTime, boolean solvedCorrectly) {
        this.endTime = endTime;
        this.solvedCorrectly = solvedCorrectly;
    }

    /**
     * Increments the attempt count for the task.
     */
    public void incrementAttempt() {
        this.attemptCount++;
    }

    /**
     * Calculates the duration taken to complete the task.
     *
     * @return Duration of the task in seconds, or -1 if the task is not completed yet
     */
    public long getTaskDuration() {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime).getSeconds();
        }
        return -1;
    }

    // Getters and setters

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public boolean isSolvedCorrectly() {
        return solvedCorrectly;
    }

    public void setSolvedCorrectly(boolean solvedCorrectly) {
        this.solvedCorrectly = solvedCorrectly;
    }

    @Override
    public String toString() {
        return "UserInteraction{" +
                "taskId='" + taskId + '\'' +
                ", taskType='" + taskType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", attemptCount=" + attemptCount +
                ", solvedCorrectly=" + solvedCorrectly +
                '}';
    }
}