package com.auroraa.dto;

import java.util.List;
import java.util.Map;

public class ComparisonRequest {
    
    private List<String> analysisIds;
    private String comparisonType;
    private Map<String, Object> options;
    
    // Getters and setters
    public List<String> getAnalysisIds() { return analysisIds; }
    public void setAnalysisIds(List<String> analysisIds) { this.analysisIds = analysisIds; }
    
    public String getComparisonType() { return comparisonType; }
    public void setComparisonType(String comparisonType) { this.comparisonType = comparisonType; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
}
