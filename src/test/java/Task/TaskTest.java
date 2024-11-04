package Task;

import Context.ContextStrategy;
import Context.VehicleContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TaskTest {

    private Task task;
    private ContextStrategy context;

    @BeforeEach
    public void setUp() {
        context = new VehicleContext();
        task = new Task(context);
    }

    @Test
    public void testCreateClassDeclaration() {
        String classDeclaration = task.getTaskCode();
        assertTrue(classDeclaration.contains("public class " + context.getClassName()),
                "Class declaration should contain 'public class' with the context class name.");
    }

    @Test
    public void testCreateVariable() {
        int initialSize = task.getTaskCode().split("=").length - 1;
        task.createVariable();
        int newSize = task.getTaskCode().split("=").length - 1;
        assertTrue(newSize > initialSize, "Variable should be added to the class.");
    }

    @Test
    public void testUniqueAttributesForVariables() {
        for (int i = 0; i < 10; i++) {
            task = new Task(context);
            List<String> attributes = task.getGeneratedAttributes();
            task.createVariable();
            task.createVariable();
            task.createVariable();
            assertEquals(attributes.size(), attributes.stream().distinct().count(),"Attributes should be unique.");
        }
    }

    @Test
    public void testCreateGetter() {
        task.createVariable();
        int initialCount = task.getTaskCode().split("get").length - 1;
        task.createGetter();
        int newCount = task.getTaskCode().split("get").length - 1;
        assertTrue(newCount > initialCount, "Getter should be added to the class.");
    }

    @Test
    public void testCreateSetter() {
        task.createVariable(); // Ensure there's an attribute to generate a setter for
        int initialCount = task.getTaskCode().split("set").length - 1;
        task.createSetter();
        int newCount = task.getTaskCode().split("set").length - 1;
        assertTrue(newCount > initialCount, "Setter should be added to the class.");
    }

    @Test
    public void testCloseClass() {
        task.closeClass();
        String taskCode = task.getTaskCode().trim();
        assertTrue(taskCode.endsWith("}"), "Class should be properly closed with '}'.");
    }
}