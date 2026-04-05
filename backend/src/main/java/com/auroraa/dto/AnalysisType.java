package com.auroraa.dto;

import java.util.List;

public class AnalysisType {
    
    private String id;
    private String name;
    private String description;
    private String category;
    private List<String> supportedLanguages;
    
    public AnalysisType() {}
    
    public AnalysisType(String id, String name, String description, String category, List<String> supportedLanguages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.supportedLanguages = supportedLanguages;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public List<String> getSupportedLanguages() { return supportedLanguages; }
    public void setSupportedLanguages(List<String> supportedLanguages) { this.supportedLanguages = supportedLanguages; }
}
