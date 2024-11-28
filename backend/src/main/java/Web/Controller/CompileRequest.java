package Web.Controller;

/**
 * Data Transfer Object (DTO) for compile requests.
 * This class represents the payload for a request to compile Java code,
 * including the class name and the code itself.
 */
public class CompileRequest {

    /**
     * The name of the class to be compiled.
     */
    private String className;

    /**
     * The Java source code to be compiled.
     */
    private String code;

    /**
     * Retrieves the name of the class to be compiled.
     *
     * @return the class name as a String.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the name of the class to be compiled.
     *
     * @param className the name of the class as a String.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Retrieves the Java source code to be compiled.
     *
     * @return the source code as a String.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the Java source code to be compiled.
     *
     * @param code the source code as a String.
     */
    public void setCode(String code) {
        this.code = code;
    }
}