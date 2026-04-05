package com.auroraa.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BatchAnalysisResponse {
    
    private String batchId;
    private List<AnalysisResponse> results;
    private Map<String, Object> summary;
    private LocalDateTime processedAt;
    
    // Getters and setters
    public String getBatchId() { return batchId; }
    public void setBatchId(String batchId) { this.batchId = batchId; }
    
    public List<AnalysisResponse> getResults() { return results; }
    public void setResults(List<AnalysisResponse> results) { this.results = results; }
    
    public Map<String, Object> getSummary() { return summary; }
    public void setSummary(Map<String, Object> summary) { this.summary = summary; }
    
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
