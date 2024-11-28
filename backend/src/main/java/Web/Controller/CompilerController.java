package Web.Controller;

import Compiler.CodeCompiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller to compile Java code and return errors or success messages.
 */
@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    private final CodeCompiler codeCompiler;

    public CompilerController() {
        this.codeCompiler = new CodeCompiler();
    }

    /**
     * Compiles the provided Java code and returns errors or a success message.
     *
     * @param request the compilation request containing the class name and code
     * @return a ResponseEntity with the compilation result
     */
    @PostMapping
    public ResponseEntity<?> compileCode(@RequestBody CompileRequest request) {
        String code = request.getCode();

        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body("Code must be provided.");
        }

        List<String> errors = codeCompiler.compile(code);
        if (errors.isEmpty()) {
            return ResponseEntity.ok("Code compiled successfully! ✅");
        } else {
            return ResponseEntity.badRequest().body(errors);
        }
    }

}