package com.auroraa.dto;

import java.time.LocalDateTime;

public class ProjectActivity {
    
    private String id;
    private String projectId;
    private String type; // created, updated, deleted, file_added, file_removed, etc.
    private String description;
    private String userId;
    private String userName;
    private LocalDateTime timestamp;
    private Map<String, Object> details;
    
    public ProjectActivity() {}
    
    public ProjectActivity(String projectId, String type, String description, String userId) {
        this.projectId = projectId;
        this.type = type;
        this.description = description;
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }
}
