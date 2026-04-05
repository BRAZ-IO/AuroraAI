package com.auroraa.dto;

import java.util.List;
import java.util.Map;

public class ApiDocumentation {
    
    private String title;
    private String version;
    private String baseUrl;
    private List<ApiEndpoint> endpoints;
    private Map<String, Object> schemas;
    private String description;
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    
    public List<ApiEndpoint> getEndpoints() { return endpoints; }
    public void setEndpoints(List<ApiEndpoint> endpoints) { this.endpoints = endpoints; }
    
    public Map<String, Object> getSchemas() { return schemas; }
    public void setSchemas(Map<String, Object> schemas) { this.schemas = schemas; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    // Inner class for API endpoints
    public static class ApiEndpoint {
        private String method;
        private String path;
        private String summary;
        private String description;
        private Map<String, Object> parameters;
        private Map<String, Object> responses;
        
        // Getters and setters
        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }
        
        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }
        
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Map<String, Object> getParameters() { return parameters; }
        public void setParameters(Map<String, Object> parameters) { this.parameters = parameters; }
        
        public Map<String, Object> getResponses() { return responses; }
        public void setResponses(Map<String, Object> responses) { this.responses = responses; }
    }
}
