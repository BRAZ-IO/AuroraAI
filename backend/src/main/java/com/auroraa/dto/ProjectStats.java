package com.auroraa.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ProjectStats {
    
    private String projectId;
    private int totalFiles;
    private long totalSize;
    private Map<String, Integer> fileTypeBreakdown;
    private Map<String, Integer> languageBreakdown;
    private LocalDateTime lastActivity;
    private int contributors;
    private int commits;
    private double averageFileSize;
    private String largestFile;
    private String mostRecentFile;
    
    public ProjectStats() {}
    
    public ProjectStats(String projectId) {
        this.projectId = projectId;
    }
    
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    
    public int getTotalFiles() { return totalFiles; }
    public void setTotalFiles(int totalFiles) { this.totalFiles = totalFiles; }
    
    public long getTotalSize() { return totalSize; }
    public void setTotalSize(long totalSize) { this.totalSize = totalSize; }
    
    public Map<String, Integer> getFileTypeBreakdown() { return fileTypeBreakdown; }
    public void setFileTypeBreakdown(Map<String, Integer> fileTypeBreakdown) { this.fileTypeBreakdown = fileTypeBreakdown; }
    
    public Map<String, Integer> getLanguageBreakdown() { return languageBreakdown; }
    public void setLanguageBreakdown(Map<String, Integer> languageBreakdown) { this.languageBreakdown = languageBreakdown; }
    
    public LocalDateTime getLastActivity() { return lastActivity; }
    public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }
    
    public int getContributors() { return contributors; }
    public void setContributors(int contributors) { this.contributors = contributors; }
    
    public int getCommits() { return commits; }
    public void setCommits(int commits) { this.commits = commits; }
    
    public double getAverageFileSize() { return averageFileSize; }
    public void setAverageFileSize(double averageFileSize) { this.averageFileSize = averageFileSize; }
    
    public String getLargestFile() { return largestFile; }
    public void setLargestFile(String largestFile) { this.largestFile = largestFile; }
    
    public String getMostRecentFile() { return mostRecentFile; }
    public void setMostRecentFile(String mostRecentFile) { this.mostRecentFile = mostRecentFile; }
}
