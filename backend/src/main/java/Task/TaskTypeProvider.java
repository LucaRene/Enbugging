package Task;

import org.springframework.stereotype.Component;

import java.util.List;

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
        return List.of(
                "SemicolonErrorTask",
                "UnclosedStringErrorTask",
                // "ReachedEndOfFileErrorTask",
                "CannotFindSymbolErrorTask",
                "ReturnTypeRequiredErrorTask",
                "IllegalStartOfExpressionErrorTask",
                "IntConvertToStringErrorTask",
                // "StringConvertToIntOrDoubleErrorTask",
                // "VariableAlreadyDefinedErrorTask",
                "IdentifierExpectedErrorTask",
                "MissingReturnValueErrorTask",
                // "MissingReturnStatementErrorTask",
                "ConstructorArgumentMismatchErrorTask"
        );
    }
}
