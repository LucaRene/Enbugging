import Task.Task;
import Compiler.CodeCompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        final int gapCount = 6;

        List<String> possibleErrors= new ArrayList<String>();
        possibleErrors.add("SemicolonErrorTask");
        possibleErrors.add("UnclosedStringErrorTask");
        possibleErrors.add("ReachedEndOfFileErrorTask");


        Task t = TaskFactory.createTask(possibleErrors.get(random.nextInt(possibleErrors.size())), gapCount);
        //Task t = TaskFactory.createTask("SemicolonErrorTask", gapCount);
        //Task t = TaskFactory.createTask("UnclosedStringErrorTask", gapCount);
        //Task t = TaskFactory.createTask("ReachedEndOfFileErrorTask", gapCount);

        System.out.println();
        System.out.println("Verändere den Code so, dass der folgende Fehler erzeugt wird:");
        System.out.println(t.getExpectedErrorMessage());
        System.out.println();
        System.out.println(t.getTaskCodeWithGaps());
        System.out.println();

        CodeCompiler compiler = new CodeCompiler();
        List<String> errors = compiler.compile(t.getClassName(), t.getTaskCodeWithoutGaps());

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