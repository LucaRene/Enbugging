package Compiler;

import javax.tools.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Utility class to compile Java code and return errors or warnings.
 */
public class CodeCompiler {

    /**
     * Compiles the given Java code string and returns any compilation errors or warnings.
     *
     * @param code the Java code to compile as a String
     * @return a list of compilation errors or warnings, or an empty list if successful
     */
    public List<String> compile(String code) {
        String className = extractClassName(code); // Extract class name dynamically
        return compile(className, code);
    }

    /**
     * Compiles the given Java code string and returns any compilation errors or warnings.
     *
     * @param className the name of the class (should match the public class name in the code)
     * @param code      the Java code to compile as a String
     * @return a list of compilation errors or warnings, or an empty list if successful
     */
    public List<String> compile(String className, String code) {
        String tempDirectory = "src/main/resources/temp/";

        try {
            Files.createDirectories(Paths.get(tempDirectory));
        } catch (IOException e) {
            return Collections.singletonList("Failed to create temp directory: " + e.getMessage());
        }

        File sourceFile = new File(tempDirectory + className + ".java");
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
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, StandardCharsets.UTF_8)) {

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

            cleanUpTemporaryFiles(tempDirectory, className, errors);

            return errors;

        } catch (IOException e) {
            return Collections.singletonList("Failed to manage file manager: " + e.getMessage());
        }
    }

    /**
     * Extracts the class name from the provided Java code.
     *
     * @param code the Java code
     * @return the extracted class name, or "UserTask" as a fallback
     */
    private String extractClassName(String code) {
        String[] lines = code.split("\\R");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("public class ")) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    return parts[2];
                }
            }
        }
        return "UserTask";
    }

    /**
     * Deletes temporary Java and class files created during compilation.
     *
     * @param tempDirectory the directory where the files are located
     * @param className     the name of the class
     * @param errors        a list of errors to add warnings to
     */
    private void cleanUpTemporaryFiles(String tempDirectory, String className, List<String> errors) {
        File sourceFile = new File(tempDirectory + className + ".java");
        File classFile = new File(tempDirectory + className + ".class");

        if (sourceFile.exists() && !sourceFile.delete()) {
            errors.add("Warning: Failed to delete temporary source file.");
        }

        if (classFile.exists() && !classFile.delete()) {
            errors.add("Warning: Failed to delete temporary .class file.");
        }
    }
}