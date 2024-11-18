import Task.Task;

public class Main {

    public static void main(String[] args) {
        Task t = TaskFactory.createTask("SemicolonErrorTask");

        System.out.println();
        System.out.println("Verändere den Code so, dass der folgende Fehler erzeugt wird:");
        System.out.println(t.getExpectedErrorMessage());
        System.out.println();
        System.out.println(t.getTaskCodeWithGaps());

        /* CodeCompiler compiler = new CodeCompiler();
        List<String> errors = compiler.compile(t.getClassName(), t.getTaskCode());

        if (errors.isEmpty()) {
            System.out.println("Compilation successful!");
        } else {
            System.out.println("Compilation failed with the following errors:");
            for (String error : errors) {
                System.out.println(error);
            }
        }
         */
    }
}