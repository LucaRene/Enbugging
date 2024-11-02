import Task.Task;

public class TaskFactory {
    public static Task createTask(String taskType) {
       return new Task();
    }
}
