package com.auroraa.dto;

import java.time.LocalDateTime;

public class AnalysisHistory {
    
    private String id;
    private String userId;
    private String analysisType;
    private String language;
    private double score;
    private int issuesFound;
    private LocalDateTime analyzedAt;
    private String status;
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getAnalysisType() { return analysisType; }
    public void setAnalysisType(String analysisType) { this.analysisType = analysisType; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    
    public int getIssuesFound() { return issuesFound; }
    public void setIssuesFound(int issuesFound) { this.issuesFound = issuesFound; }
    
    public LocalDateTime getAnalyzedAt() { return analyzedAt; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
