package com.auroraa.dto;

import java.util.List;

public class SecurityScanResult {
    
    private List<SecurityVulnerability> vulnerabilities;
    private int riskScore;
    private String riskLevel;
    private List<String> recommendations;
    private boolean hasCriticalIssues;
    
    // Getters and setters
    public List<SecurityVulnerability> getVulnerabilities() { return vulnerabilities; }
    public void setVulnerabilities(List<SecurityVulnerability> vulnerabilities) { this.vulnerabilities = vulnerabilities; }
    
    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    
    public boolean isHasCriticalIssues() { return hasCriticalIssues; }
    public void setHasCriticalIssues(boolean hasCriticalIssues) { this.hasCriticalIssues = hasCriticalIssues; }
    
    // Inner class for SecurityVulnerability
    public static class SecurityVulnerability {
        private String type;
        private String severity;
        private String description;
        private int line;
        private String recommendation;
        private String cweId;
        
        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public int getLine() { return line; }
        public void setLine(int line) { this.line = line; }
        
        public String getRecommendation() { return recommendation; }
        public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
        
        public String getCweId() { return cweId; }
        public void setCweId(String cweId) { this.cweId = cweId; }
    }
}
