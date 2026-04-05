package com.auroraa.dto;

import java.util.List;
import java.util.Map;

public class ProjectAnalysis {
    
    private String repositoryUrl;
    private int totalFiles;
    private List<String> languages;
    private List<String> technologies;
    private Map<String, Integer> fileTypes;
    private String projectType;
    private boolean hasTests;
    private boolean hasDocumentation;
    
    // Getters and setters
    public String getRepositoryUrl() { return repositoryUrl; }
    public void setRepositoryUrl(String repositoryUrl) { this.repositoryUrl = repositoryUrl; }
    
    public int getTotalFiles() { return totalFiles; }
    public void setTotalFiles(int totalFiles) { this.totalFiles = totalFiles; }
    
    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
    
    public List<String> getTechnologies() { return technologies; }
    public void setTechnologies(List<String> technologies) { this.technologies = technologies; }
    
    public Map<String, Integer> getFileTypes() { return fileTypes; }
    public void setFileTypes(Map<String, Integer> fileTypes) { this.fileTypes = fileTypes; }
    
    public String getProjectType() { return projectType; }
    public void setProjectType(String projectType) { this.projectType = projectType; }
    
    public boolean isHasTests() { return hasTests; }
    public void setHasTests(boolean hasTests) { this.hasTests = hasTests; }
    
    public boolean isHasDocumentation() { return hasDocumentation; }
    public void setHasDocumentation(boolean hasDocumentation) { this.hasDocumentation = hasDocumentation; }
}
