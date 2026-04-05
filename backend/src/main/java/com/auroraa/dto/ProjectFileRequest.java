package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectFileRequest {
    
    @NotBlank(message = "File name cannot be blank")
    @Size(max = 255, message = "File name cannot exceed 255 characters")
    private String name;
    
    @NotBlank(message = "File path cannot be blank")
    @Size(max = 500, message = "File path cannot exceed 500 characters")
    private String path;
    
    @NotBlank(message = "File type cannot be blank")
    private String type;
    
    private String content;
    private String language;
    private String description;
    
    public ProjectFileRequest() {}
    
    public ProjectFileRequest(String name, String path, String type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
