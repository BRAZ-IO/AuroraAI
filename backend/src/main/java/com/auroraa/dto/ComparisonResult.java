package com.auroraa.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ComparisonResult {
    
    private String comparisonId;
    private List<AnalysisComparison> comparisons;
    private Map<String, Object> summary;
    private String winner;
    private LocalDateTime comparedAt;
    
    // Getters and setters
    public String getComparisonId() { return comparisonId; }
    public void setComparisonId(String comparisonId) { this.comparisonId = comparisonId; }
    
    public List<AnalysisComparison> getComparisons() { return comparisons; }
    public void setComparisons(List<AnalysisComparison> comparisons) { this.comparisons = comparisons; }
    
    public Map<String, Object> getSummary() { return summary; }
    public void setSummary(Map<String, Object> summary) { this.summary = summary; }
    
    public String getWinner() { return winner; }
    public void setWinner(String winner) { this.winner = winner; }
    
    public LocalDateTime getComparedAt() { return comparedAt; }
    public void setComparedAt(LocalDateTime comparedAt) { this.comparedAt = comparedAt; }
    
    // Inner class for AnalysisComparison
    public static class AnalysisComparison {
        private String analysisId;
        private double score;
        private int issueCount;
        private Map<String, Object> metrics;
        private String rank;
        
        // Getters and setters
        public String getAnalysisId() { return analysisId; }
        public void setAnalysisId(String analysisId) { this.analysisId = analysisId; }
        
        public double getScore() { return score; }
        public void setScore(double score) { this.score = score; }
        
        public int getIssueCount() { return issueCount; }
        public void setIssueCount(int issueCount) { this.issueCount = issueCount; }
        
        public Map<String, Object> getMetrics() { return metrics; }
        public void setMetrics(Map<String, Object> metrics) { this.metrics = metrics; }
        
        public String getRank() { return rank; }
        public void setRank(String rank) { this.rank = rank; }
    }
}
