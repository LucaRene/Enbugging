package Web.Service;

import Task.Task;
import Task.TaskFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service to handle task generation logic.
 */
@Service
public class TaskService {

    /**
     * Generates a random task.
     *
     * @return a Task instance
     */
    public Task generateRandomTask() {
        Random random = new Random();
        List<String> possibleErrors = new ArrayList<String>();
        possibleErrors.add("SemicolonErrorTask");
        possibleErrors.add("UnclosedStringErrorTask");
        possibleErrors.add("ReachedEndOfFileErrorTask");

        return TaskFactory.createTask(possibleErrors.get(random.nextInt(possibleErrors.size())), 6);
    }

    /**
     * Generates a random task and returns the task code with gaps as HTML.
     *
     * @return the task code with gaps as HTML
     */
    public String getTaskAsHtmlWithDescription() {
        Task task = generateRandomTask();
        return "<html>" +
                "<body>" +
                "<h1>Aufgabe</h1>" +
                "<p>Verändere den Code, damit folgender Fehler entsteht:</p>" +
                "<h2>" + task.getExpectedErrorMessage() + "</h2>" +
                "<pre>" + task.getTaskCodeWithGaps() + "</pre>" +
                "</body>" +
                "</html>";
    }
}