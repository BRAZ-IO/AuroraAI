package com.auroraa.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CodeGenerationResponse {
    
    private String id;
    private String generatedCode;
    private String language;
    private String explanation;
    private List<String> suggestions;
    private LocalDateTime generatedAt;
    private int processingTimeMs;
    
    public CodeGenerationResponse() {}
    
    public CodeGenerationResponse(String id, String generatedCode, String language) {
        this.id = id;
        this.generatedCode = generatedCode;
        this.language = language;
        this.generatedAt = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getGeneratedCode() { return generatedCode; }
    public void setGeneratedCode(String generatedCode) { this.generatedCode = generatedCode; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    
    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
    
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    
    public int getProcessingTimeMs() { return processingTimeMs; }
    public void setProcessingTimeMs(int processingTimeMs) { this.processingTimeMs = processingTimeMs; }
}
