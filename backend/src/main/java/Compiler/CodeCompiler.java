package Compiler;

import javax.tools.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CodeCompiler {

    /**
     * Compiles the given Java code string and returns any compilation errors or warnings.
     *
     * @param className the name of the class (should match the public class name in the code)
     * @param code the Java code to compile as a String
     * @return a list of compilation errors or warnings, or an empty list if successful
     */
    public List<String> compile(String className, String code) {

        File sourceFile = new File("src/main/resources/temp/" + className + ".java");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), StandardCharsets.UTF_8))) {
            writer.write(code);
        } catch (IOException e) {
            return Collections.singletonList("Failed to write source file: " + e.getMessage());
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return Collections.singletonList("Java Compiler not available. Ensure JDK is being used.");
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, StandardCharsets.UTF_8);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(sourceFile);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        List<String> errors = new ArrayList<>();
        if (!success) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                errors.add(String.format("Error on line %d: %s",
                        diagnostic.getLineNumber(),
                        diagnostic.getMessage(null)));
            }
        }

        try {
            fileManager.close();
            if (!sourceFile.delete()) {
                errors.add("Warning: Failed to delete temporary source file.");
            }

            File classFile = new File("src/main/resources/temp/" + className + ".class");
            if (classFile.exists() && !classFile.delete()) {
                errors.add("Warning: Failed to delete compiled .class file.");
            }
        } catch (IOException e) {
            errors.add("Failed to close file manager: " + e.getMessage());
        }

        return errors;
    }
}