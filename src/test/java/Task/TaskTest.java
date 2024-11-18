package Task;

import Context.ContextStrategy;
import Context.VehicleContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for the Task class to ensure correct generation of
 * class structures, variables, getters, and setters.
 */
public class TaskTest {

    private Task task;
    private ContextStrategy context;

    /**
     * Sets up the test with a VehicleContext for the Task class.
     */
    @BeforeEach
    public void setUp() {
        context = new VehicleContext();
        task = new SemicolonErrorTask(context);
    }

    /**
     * Tests if the class declaration is correctly generated and
     * includes the context's class name.
     */
    @Test
    public void testCreateClassDeclaration() {
        String classDeclaration = task.getTaskCode();
        assertTrue(classDeclaration.contains("public class " + context.getClassName()),
                "Class declaration should contain 'public class' with the context class name.");
    }

    /**
     * Tests if a variable is successfully added to the class by
     * comparing the count of assignment operators ('=') in the generated code.
     */
    @Test
    public void testCreateVariable() {
        int initialSize = task.getTaskCode().split("=").length - 1;
        task.createVariable();
        int newSize = task.getTaskCode().split("=").length - 1;
        assertTrue(newSize > initialSize, "Variable should be added to the class.");
    }

    /**
     * Tests if the generated attributes are unique by ensuring
     * no duplicate attribute names are created.
     */
    @Test
    public void testUniqueAttributesForVariables() {
        for (int i = 0; i < 10; i++) {
            task = new SemicolonErrorTask(context);
            List<String> attributes = task.getGeneratedAttributes();
            task.createVariable();
            task.createVariable();
            task.createVariable();
            assertEquals(attributes.size(), attributes.stream().distinct().count(), "Attributes should be unique.");
        }
    }

    /**
     * Tests if a getter method is successfully added to the class
     * by comparing the count of 'get' occurrences in the generated code.
     */
    @Test
    public void testCreateGetter() {
        task.createVariable(); // Ensure there's an attribute to generate a getter for
        int initialCount = task.getTaskCode().split("get").length - 1;
        task.createGetter();
        int newCount = task.getTaskCode().split("get").length - 1;
        assertTrue(newCount > initialCount, "Getter should be added to the class.");
    }

    /**
     * Tests if a setter method is successfully added to the class
     * by comparing the count of 'set' occurrences in the generated code.
     */
    @Test
    public void testCreateSetter() {
        task.createVariable(); // Ensure there's an attribute to generate a setter for
        int initialCount = task.getTaskCode().split("set").length - 1;
        task.createSetter();
        int newCount = task.getTaskCode().split("set").length - 1;
        assertTrue(newCount > initialCount, "Setter should be added to the class.");
    }

    /**
     * Tests if the class is properly closed with a closing brace ('}').
     */
    @Test
    public void testCloseClass() {
        task.closeClass();
        String taskCode = task.getTaskCode().trim();
        assertTrue(taskCode.endsWith("}"), "Class should be properly closed with '}'.");
    }
}