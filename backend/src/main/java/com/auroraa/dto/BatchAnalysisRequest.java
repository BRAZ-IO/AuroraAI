package com.auroraa.dto;

import java.util.List;
import java.util.Map;

public class BatchAnalysisRequest {
    
    private List<AnalysisFile> files;
    private String analysisType;
    private Map<String, Object> options;
    
    // Getters and setters
    public List<AnalysisFile> getFiles() { return files; }
    public void setFiles(List<AnalysisFile> files) { this.files = files; }
    
    public String getAnalysisType() { return analysisType; }
    public void setAnalysisType(String analysisType) { this.analysisType = analysisType; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
    
    // Inner class for AnalysisFile
    public static class AnalysisFile {
        private String name;
        private String content;
        private String language;
        private String path;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
        
        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }
    }
}
