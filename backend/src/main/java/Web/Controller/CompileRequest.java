package Web.Controller;

/**
 * Data Transfer Object (DTO) for compile requests.
 * Represents the payload for a request to compile Java source code and evaluate the output.
 */
public class CompileRequest {

    /**
     * The Java source code to be compiled.
     */
    private String code;

    /**
     * The expected error message that the provided code should produce during compilation.
     */
    private String expectedError;

    /**
     * Retrieves the Java source code to be compiled.
     *
     * @return the Java source code as a string.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the Java source code to be compiled.
     *
     * @param code the Java source code as a string.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retrieves the expected error message that the code should produce.
     *
     * @return the expected error message as a string.
     */
    public String getExpectedError() {
        return expectedError;
    }

    /**
     * Sets the expected error message that the code should produce.
     *
     * @param expectedError the expected error message as a string.
     */
    public void setExpectedError(String expectedError) {
        this.expectedError = expectedError;
    }
}