package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnalysisRequest {
    
    @NotBlank(message = "Code cannot be blank")
    @Size(max = 10000, message = "Code cannot exceed 10000 characters")
    private String code;
    
    @NotBlank(message = "Language cannot be blank")
    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;
    
    @NotBlank(message = "Analysis type cannot be blank")
    private String analysisType; // security, performance, quality, style
    
    private String fileName;
    private boolean includeSuggestions = true;
    private Map<String, Object> options;
    
    public AnalysisRequest() {}
    
    public AnalysisRequest(String code, String language, String analysisType) {
        this.code = code;
        this.language = language;
        this.analysisType = analysisType;
    }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public String getAnalysisType() { return analysisType; }
    public void setAnalysisType(String analysisType) { this.analysisType = analysisType; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public boolean isIncludeSuggestions() { return includeSuggestions; }
    public void setIncludeSuggestions(boolean includeSuggestions) { this.includeSuggestions = includeSuggestions; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
}
