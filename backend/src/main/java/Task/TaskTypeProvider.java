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

    public List<String> getTypeErrorTasks(){
        return List.of(
                "IntConvertToStringErrorTask",
                "StringConvertToIntOrDoubleErrorTask",
                "ConstructorArgumentMismatchErrorTask"
        );
    }

    public List<String> getSyntaxErrorTasks(){
        return List.of(
                "SemicolonErrorTask",
                "UnclosedStringErrorTask",
                "ReachedEndOfFileErrorTask",
                "IllegalStartOfExpressionErrorTask"
        );
    }

    public List<String> getDeclarationErrorTasks(){
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