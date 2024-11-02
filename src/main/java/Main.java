import Task.Task;

public class Main {

    public static void main(String[] args) {
        Task t = TaskFactory.createTask("Task");
        System.out.println(t.getTaskCode());
    }
}