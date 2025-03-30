package Web.Controller;

import Task.Task;
import Web.Service.TaskService;
import Web.Tracking.TrackingService;
import Web.Tracking.UserInteraction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to expose tasks via HTTP endpoints.
 */
@RestController
public class TaskController {

    private final TaskService taskService;
    private final TrackingService trackingService;

    public TaskController(TaskService taskService, TrackingService trackingService) {
        this.taskService = taskService;
        this.trackingService = trackingService;
    }

    @GetMapping("/config-used")
    public boolean isConfigUsed() {
        return taskService.hasPredefinedTasks();
    }

    @GetMapping("/task")
    public Task getTask(@RequestParam("type") String type) {
        Task t = taskService.generateRandomTask(type);
        UserInteraction interaction = new UserInteraction(t.getTaskType());
        trackingService.recordInteraction(interaction);
        return t;
    }
}