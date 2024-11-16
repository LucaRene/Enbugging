import Task.Task;
import Compiler.CodeCompiler;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Task t = TaskFactory.createTask("Task");
        System.out.println(t.getTaskCode());

        CodeCompiler compiler = new CodeCompiler();
        List<String> errors =  compiler.compile(t.getClassName(), t.getTaskCode());

        if (errors.isEmpty()) {
            System.out.println("Compilation successful!");
        } else {
            System.out.println("Compilation failed with the following errors:");
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}