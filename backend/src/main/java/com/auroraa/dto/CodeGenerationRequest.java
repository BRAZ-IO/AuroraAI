package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CodeGenerationRequest {
    
    @NotBlank(message = "Prompt cannot be blank")
    @Size(max = 2000, message = "Prompt cannot exceed 2000 characters")
    private String prompt;
    
    @NotBlank(message = "Language cannot be blank")
    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;
    
    @Size(max = 50, message = "Framework cannot exceed 50 characters")
    private String framework;
    
    @Size(max = 1000, message = "Context cannot exceed 1000 characters")
    private String context;
    
    private String style = "default"; // default, functional, oop, etc.
    
    public CodeGenerationRequest() {}
    
    public CodeGenerationRequest(String prompt, String language) {
        this.prompt = prompt;
        this.language = language;
    }
    
    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public String getFramework() { return framework; }
    public void setFramework(String framework) { this.framework = framework; }
    
    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
    
    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }
}
