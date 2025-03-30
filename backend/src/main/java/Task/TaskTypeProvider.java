package Task;

import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Component responsible for providing lists of predefined task types, grouped by error categories.
 * Used for generating specific tasks according to defined categories or user preferences.
 */
@Component
public class TaskTypeProvider {

    /**
     * Retrieves a complete list containing all available task types.
     *
     * @return a list of strings representing all supported task types.
     */
    public List<String> getAllTaskTypes() {
        return List.of(
                "SemicolonErrorTask",
                "UnclosedStringErrorTask",
                "ReachedEndOfFileErrorTask",
                "CannotFindSymbolErrorTask",
                "ReturnTypeRequiredErrorTask",
                "IllegalStartOfExpressionErrorTask",
                "IntConvertToStringErrorTask",
                "StringConvertToIntOrDoubleErrorTask",
                "VariableAlreadyDefinedErrorTask",
                "IdentifierExpectedErrorTask",
                "MissingReturnValueErrorTask",
                "MissingReturnStatementErrorTask",
                "ConstructorArgumentMismatchErrorTask"
        );
    }

    /**
     * Provides a list of task types specifically related to type errors.
     * These tasks involve data type conversion and mismatch issues.
     *
     * @return a list of task type strings representing type error tasks.
     */
    public List<String> getTypeErrorTasks() {
        return List.of(
                "IntConvertToStringErrorTask",
                "StringConvertToIntOrDoubleErrorTask",
                "ConstructorArgumentMismatchErrorTask"
        );
    }

    /**
     * Provides a list of task types specifically related to syntax errors.
     * These tasks involve typical mistakes in Java syntax such as missing semicolons or incomplete expressions.
     *
     * @return a list of task type strings representing syntax error tasks.
     */
    public List<String> getSyntaxErrorTasks() {
        return List.of(
                "SemicolonErrorTask",
                "UnclosedStringErrorTask",
                "ReachedEndOfFileErrorTask",
                "IllegalStartOfExpressionErrorTask"
        );
    }

    /**
     * Provides a list of task types specifically related to declaration errors.
     * These tasks involve errors in variable or method declarations, including duplicates, missing identifiers, or return types.
     *
     * @return a list of task type strings representing declaration error tasks.
     */
    public List<String> getDeclarationErrorTasks() {
        return List.of(
                "VariableAlreadyDefinedErrorTask",
                "IdentifierExpectedErrorTask",
                "CannotFindSymbolErrorTask",
                "InvalidMethodDeclarationErrorTask",
                "MissingReturnValueErrorTask",
                "MissingReturnStatementErrorTask"
        );
    }
}