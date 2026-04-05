package com.auroraa.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ProjectMetadata {
    
    private String projectId;
    private Map<String, Object> customFields;
    private Map<String, Object> configuration;
    private Map<String, Object> integrations;
    private Map<String, Object> tags;
    private LocalDateTime lastUpdated;
    private String updatedBy;
    
    public ProjectMetadata() {}
    
    public ProjectMetadata(String projectId) {
        this.projectId = projectId;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    
    public Map<String, Object> getCustomFields() { return customFields; }
    public void setCustomFields(Map<String, Object> customFields) { this.customFields = customFields; }
    
    public Map<String, Object> getConfiguration() { return configuration; }
    public void setConfiguration(Map<String, Object> configuration) { this.configuration = configuration; }
    
    public Map<String, Object> getIntegrations() { return integrations; }
    public void setIntegrations(Map<String, Object> integrations) { this.integrations = integrations; }
    
    public Map<String, Object> getTags() { return tags; }
    public void setTags(Map<String, Object> tags) { this.tags = tags; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}
