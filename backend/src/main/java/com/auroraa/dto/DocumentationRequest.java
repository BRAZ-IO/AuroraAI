package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;

public class DocumentationRequest {
    
    @NotBlank(message = "Repository URL cannot be blank")
    @Size(max = 500, message = "Repository URL cannot exceed 500 characters")
    private String repositoryUrl;
    
    @NotBlank(message = "Template cannot be blank")
    @Size(max = 50, message = "Template cannot exceed 50 characters")
    private String template;
    
    private Map<String, Object> options;
    
    public DocumentationRequest() {}
    
    public DocumentationRequest(String repositoryUrl, String template) {
        this.repositoryUrl = repositoryUrl;
        this.template = template;
    }
    
    public String getRepositoryUrl() { return repositoryUrl; }
    public void setRepositoryUrl(String repositoryUrl) { this.repositoryUrl = repositoryUrl; }
    
    public String getTemplate() { return template; }
    public void setTemplate(String template) { this.template = template; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
}
