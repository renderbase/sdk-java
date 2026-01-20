package com.renderbase.exceptions;

/**
 * Exception thrown when an error occurs during Renderbase API operations.
 */
public class RenderbaseException extends RuntimeException {

    private final String code;
    private final int statusCode;

    public RenderbaseException(String message) {
        this(message, null, 0);
    }

    public RenderbaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
        this.statusCode = 0;
    }

    public RenderbaseException(String message, String code, int statusCode) {
        super(message);
        this.code = code;
        this.statusCode = statusCode;
    }

    /**
     * Returns the error code from the API.
     *
     * @return Error code or null
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return HTTP status code or 0 if not applicable
     */
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RenderbaseException{");
        sb.append("message='").append(getMessage()).append("'");
        if (code != null) {
            sb.append(", code='").append(code).append("'");
        }
        if (statusCode > 0) {
            sb.append(", statusCode=").append(statusCode);
        }
        sb.append("}");
        return sb.toString();
    }
}
