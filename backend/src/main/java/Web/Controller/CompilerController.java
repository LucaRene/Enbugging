package Web.Controller;

import Compiler.CodeCompiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller to compile Java code and evaluate if the task is solved correctly.
 */
@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    private final CodeCompiler codeCompiler;

    public CompilerController() {
        this.codeCompiler = new CodeCompiler();
    }

    /**
     * Compiles the provided Java code and evaluates if the task is solved correctly.
     *
     * @param request the compilation request containing the code and expected error
     * @return a ResponseEntity with the expected error, actual compiler output, and evaluation result
     */
    @PostMapping
    public ResponseEntity<?> compileCode(@RequestBody CompileRequest request) {
        String code = request.getCode();
        String expectedError = request.getExpectedError();

        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Code must be provided.",
                    "actualError", "",
                    "evaluation", "Incorrect"
            ));
        }



        List<String> errors = codeCompiler.compile(code);
        String actualError = errors.isEmpty() ? "No errors" : errors.get(0);

        String normalizedExpectedError = expectedError.replace("\n", "").trim();
        String normalizedActualError = actualError.replace("\n", "").trim();

        boolean isCorrect = normalizedActualError.contains(normalizedExpectedError);

        return ResponseEntity.ok(Map.of(
                "status", isCorrect ? "success" : "error",
                "expectedError", expectedError,
                "actualError", actualError,
                "evaluation", isCorrect ? "Richtig! ✅ Gut gelöst! \n Gehe weiter zur nächsten Aufgabe!"
                        : "Leider falsch! ❌ \n Versuche es gleich nochmal!"
        ));
    }
}