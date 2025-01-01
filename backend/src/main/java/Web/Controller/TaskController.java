package Web.Controller;

import Task.Task;
import Web.Service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to expose tasks via HTTP endpoints.
 */
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task")
    public Task getTask() {
        return taskService.generateRandomTask();
    }
}