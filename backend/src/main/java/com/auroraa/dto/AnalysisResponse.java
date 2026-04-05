package com.auroraa.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AnalysisResponse {
    
    private String id;
    private List<CodeIssue> issues;
    private double score; // 0.0 to 10.0
    private String summary;
    private List<String> improvements;
    private LocalDateTime analyzedAt;
    private int linesAnalyzed;
    private String analysisType;
    private Map<String, Object> metrics;
    
    public AnalysisResponse() {}
    
    public AnalysisResponse(String id, List<CodeIssue> issues, double score) {
        this.id = id;
        this.issues = issues;
        this.score = score;
        this.analyzedAt = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public List<CodeIssue> getIssues() { return issues; }
    public void setIssues(List<CodeIssue> issues) { this.issues = issues; }
    
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public List<String> getImprovements() { return improvements; }
    public void setImprovements(List<String> improvements) { this.improvements = improvements; }
    
    public LocalDateTime getAnalyzedAt() { return analyzedAt; }
    public void setAnalyzedAt(LocalDateTime analyzedAt) { this.analyzedAt = analyzedAt; }
    
    public int getLinesAnalyzed() { return linesAnalyzed; }
    public void setLinesAnalyzed(int linesAnalyzed) { this.linesAnalyzed = linesAnalyzed; }
    
    public String getAnalysisType() { return analysisType; }
    public void setAnalysisType(String analysisType) { this.analysisType = analysisType; }
    
    public Map<String, Object> getMetrics() { return metrics; }
    public void setMetrics(Map<String, Object> metrics) { this.metrics = metrics; }
    
    // Inner class for code issues
    public static class CodeIssue {
        private String severity; // low, medium, high, critical
        private String message;
        private int line;
        private String suggestion;
        private String category; // security, performance, style, etc.
        
        public CodeIssue() {}
        
        public CodeIssue(String severity, String message, int line) {
            this.severity = severity;
            this.message = message;
            this.line = line;
        }
        
        // Getters and setters
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public int getLine() { return line; }
        public void setLine(int line) { this.line = line; }
        
        public String getSuggestion() { return suggestion; }
        public void setSuggestion(String suggestion) { this.suggestion = suggestion; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
}
