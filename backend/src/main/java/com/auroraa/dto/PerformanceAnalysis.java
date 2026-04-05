package com.auroraa.dto;

import java.util.List;
import java.util.Map;

public class PerformanceAnalysis {
    
    private List<PerformanceIssue> performanceIssues;
    private double performanceScore;
    private List<String> optimizations;
    private Map<String, Object> metrics;
    private String bottleneck;
    
    // Getters and setters
    public List<PerformanceIssue> getPerformanceIssues() { return performanceIssues; }
    public void setPerformanceIssues(List<PerformanceIssue> performanceIssues) { this.performanceIssues = performanceIssues; }
    
    public double getPerformanceScore() { return performanceScore; }
    public void setPerformanceScore(double performanceScore) { this.performanceScore = performanceScore; }
    
    public List<String> getOptimizations() { return optimizations; }
    public void setOptimizations(List<String> optimizations) { this.optimizations = optimizations; }
    
    public Map<String, Object> getMetrics() { return metrics; }
    public void setMetrics(Map<String, Object> metrics) { this.metrics = metrics; }
    
    public String getBottleneck() { return bottleneck; }
    public void setBottleneck(String bottleneck) { this.bottleneck = bottleneck; }
    
    // Inner class for PerformanceIssue
    public static class PerformanceIssue {
        private String type;
        private String severity;
        private String description;
        private int line;
        private String impact;
        private String suggestion;
        
        public PerformanceIssue() {}
        
        public PerformanceIssue(String type, String severity, String description, int line, String impact, String suggestion) {
            this.type = type;
            this.severity = severity;
            this.description = description;
            this.line = line;
            this.impact = impact;
            this.suggestion = suggestion;
        }
        
        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public int getLine() { return line; }
        public void setLine(int line) { this.line = line; }
        
        public String getImpact() { return impact; }
        public void setImpact(String impact) { this.impact = impact; }
        
        public String getSuggestion() { return suggestion; }
        public void setSuggestion(String suggestion) { this.suggestion = suggestion; }
    }
}
