package com.auroraa.dto;

import java.time.LocalDateTime;

public class DocumentationResponse {
    
    private String id;
    private String content;
    private String format;
    private LocalDateTime generatedAt;
    private int wordCount;
    private String message;
    private String repositoryUrl;
    private String template;
    
    public DocumentationResponse() {}
    
    public DocumentationResponse(String id, String content, String format) {
        this.id = id;
        this.content = content;
        this.format = format;
        this.generatedAt = LocalDateTime.now();
        this.wordCount = content != null ? content.split("\\s+").length : 0;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    
    public int getWordCount() { return wordCount; }
    public void setWordCount(int wordCount) { this.wordCount = wordCount; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getRepositoryUrl() { return repositoryUrl; }
    public void setRepositoryUrl(String repositoryUrl) { this.repositoryUrl = repositoryUrl; }
    
    public String getTemplate() { return template; }
    public void setTemplate(String template) { this.template = template; }
}
