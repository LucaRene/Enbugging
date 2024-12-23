package Web.Controller;

/**
 * Data Transfer Object (DTO) for compile requests.
 * Represents the payload for a request to compile Java code and evaluate it.
 */
public class CompileRequest {

    /**
     * The Java source code to be compiled.
     */
    private String code;

    /**
     * The expected error message that the code should produce.
     */
    private String expectedError;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpectedError() {
        return expectedError;
    }

    public void setExpectedError(String expectedError) {
        this.expectedError = expectedError;
    }
}