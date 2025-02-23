package Web.Controller;

import Compiler.CodeCompiler;
import Web.Tracking.TrackingService;
import Web.Tracking.UserInteraction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller to compile Java code and evaluate if the task is solved correctly.
 */
@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    private final CodeCompiler codeCompiler;
    private final TrackingService trackingService;

    /**
     * Constructor to initialize the compiler controller.
     *
     * @param trackingService The tracking service to use
     */
    public CompilerController(TrackingService trackingService) {
        this.codeCompiler = new CodeCompiler();
        this.trackingService = trackingService;
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

        if (code == null) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Code must be provided.");
            response.put("actualError", "");
            response.put("evaluation", "Incorrect");

            return ResponseEntity.badRequest().body(response);
        }



        List<String> errors = codeCompiler.compile(code);
        String actualError = errors.isEmpty() ? "No errors" : errors.get(0);

        String normalizedExpectedError = normalizeErrorMessage(expectedError);
        String normalizedActualError = normalizeErrorMessage(actualError);

        boolean isCorrect = normalizedActualError.contains(normalizedExpectedError);

        UserInteraction interaction = trackingService.getUserInteractions()
                .get(trackingService.getUserInteractions().size() - 1);
        interaction.setSolvedCorrectly(isCorrect);
        interaction.setAttemptCount(interaction.getAttemptCount() + 1);
        interaction.setEndTime(LocalDateTime.now());

        if (isCorrect) {
            if (interaction.getAttemptCount() == 1) {
                trackingService.decreaseTaskPerformance(interaction.getTaskType());
            } else if (interaction.getAttemptCount() > 2) {
                trackingService.increaseTaskPerformance(interaction.getTaskType());
            }
        }

        //trackingService.rewriteCSV();

        Map<String, String> response = new HashMap<>();
        response.put("status", isCorrect ? "success" : "error");
        response.put("expectedError", expectedError);
        response.put("actualError", actualError);
        response.put("evaluation", isCorrect
                ? "Richtig! ✅ Gut gelöst! \n Gehe weiter zur nächsten Aufgabe!"
                : "Leider falsch! ❌ \n Versuche es gleich nochmal!");

        return ResponseEntity.ok(response);
    }

    /**
     * Normalizes the error message by removing unnecessary whitespace and line breaks.
     */
    private String normalizeErrorMessage(String errorMessage) {
        if (errorMessage == null) {
            return "";
        }
        return errorMessage
                .replaceAll("\\s+", "")
                .replace("\n", "")
                .replace("\r", "")
                .replace("\t", "")
                .trim();
    }

}