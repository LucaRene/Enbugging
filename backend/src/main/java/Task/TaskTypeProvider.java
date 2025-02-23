package Task;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.Array;


/**
 * Provides a list of all available task types.
 */
@Component
public class TaskTypeProvider {

    /**
     * Returns a list of task types.
     *
     * @return List of task types.
     */
    public List<String> getTaskTypes() {
        List<String> taskTypes = new ArrayList<String>();
        taskTypes.add("SemicolonErrorTask");
        taskTypes.add("UnclosedStringErrorTask");
        taskTypes.add("CannotFindSymbolErrorTask");
        taskTypes.add("ReturnTypeRequiredErrorTask");
        //taskTypes.add("IllegalStartOfExpressionErrorTask");
        taskTypes.add("IntConvertToStringErrorTask");
        taskTypes.add("IdentifierExpectedErrorTask");
        //taskTypes.add("MissingReturnValueErrorTask");
        //taskTypes.add("ConstructorArgumentMismatchErrorTask");
        taskTypes.add("ReachedEndOfFileErrorTask");
        //taskTypes.add("StringConvertToIntOrDoubleErrorTask");
        //taskTypes.add("VariableAlreadyDefinedErrorTask");
        //taskTypes.add("MissingReturnStatementErrorTask");

        return taskTypes;
    }
}
