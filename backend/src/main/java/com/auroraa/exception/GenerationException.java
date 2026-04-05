package com.auroraa.exception;

public class GenerationException extends RuntimeException {
    
    private String errorCode;
    private String details;
    
    public GenerationException(String message) {
        super(message);
    }
    
    public GenerationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public GenerationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public GenerationException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public GenerationException(String message, String errorCode, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
    
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
