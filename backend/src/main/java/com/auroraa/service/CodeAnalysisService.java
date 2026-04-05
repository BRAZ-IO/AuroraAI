package com.auroraa.service;

import com.auroraa.dto.*;
import com.auroraa.dto.AnalysisResponse.CodeIssue;
import com.auroraa.dto.SecurityScanResult.SecurityVulnerability;
import com.auroraa.dto.PerformanceAnalysis.PerformanceIssue;
import com.auroraa.dto.ComparisonResult.AnalysisComparison;
import com.auroraa.dto.BatchAnalysisRequest.AnalysisFile;
import com.auroraa.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CodeAnalysisService {
    
    private static final Logger logger = LoggerFactory.getLogger(CodeAnalysisService.class);
    
    private static final List<String> SUPPORTED_LANGUAGES = List.of(
        "java", "javascript", "typescript", "python", "go", "rust", "c", "cpp", "csharp", "php", "ruby"
    );
    
    private static final List<AnalysisType> ANALYSIS_TYPES = List.of(
        new AnalysisType("security", "Security Analysis", "Identify security vulnerabilities and risks", "security", SUPPORTED_LANGUAGES),
        new AnalysisType("performance", "Performance Analysis", "Analyze performance bottlenecks and optimization opportunities", "performance", SUPPORTED_LANGUAGES),
        new AnalysisType("quality", "Code Quality", "Assess overall code quality and maintainability", "quality", SUPPORTED_LANGUAGES),
        new AnalysisType("style", "Style Guide", "Check coding style and best practices compliance", "style", SUPPORTED_LANGUAGES)
    );
    
    public AnalysisResponse analyzeCode(AnalysisRequest request) {
        logger.info("Starting code analysis for language: {}, type: {}", 
                   request.getLanguage(), request.getAnalysisType());
        
        validateAnalysisRequest(request);
        
        try {
            List<AnalysisResponse.CodeIssue> issues = analyzeCodeIssues(request);
            double score = calculateScore(issues, request.getAnalysisType());
            
            AnalysisResponse response = new AnalysisResponse(
                UUID.randomUUID().toString(),
                issues,
                score
            );
            
            response.setAnalysisType(request.getAnalysisType());
            response.setLinesAnalyzed(request.getCode().split("\n").length);
            response.setSummary(generateSummary(issues, score, request.getAnalysisType()));
            response.setImprovements(generateImprovements(request));
            response.setMetrics(generateMetrics(request, issues, score));
            
            logger.info("Code analysis completed with score: {}, issues: {}", score, issues.size());
            return response;
            
        } catch (Exception e) {
            logger.error("Error during code analysis: {}", e.getMessage(), e);
            throw new ValidationException("Failed to analyze code: " + e.getMessage());
        }
    }
    
    public BatchAnalysisResponse analyzeBatch(BatchAnalysisRequest request) {
        logger.info("Starting batch analysis for {} files", request.getFiles().size());
        
        try {
            BatchAnalysisResponse response = new BatchAnalysisResponse();
            response.setBatchId(UUID.randomUUID().toString());
            response.setProcessedAt(LocalDateTime.now());
            
            List<AnalysisResponse> results = new ArrayList<>();
            Map<String, Object> summary = new HashMap<>();
            
            int totalIssues = 0;
            double totalScore = 0.0;
            Map<String, Integer> issuesByType = new HashMap<>();
            
            for (AnalysisFile file : request.getFiles()) {
                AnalysisRequest analysisRequest = new AnalysisRequest(
                    file.getContent(), 
                    file.getLanguage(), 
                    request.getAnalysisType()
                );
                analysisRequest.setFileName(file.getName());
                
                AnalysisResponse fileResult = analyzeCode(analysisRequest);
                results.add(fileResult);
                
                totalIssues += fileResult.getIssues().size();
                totalScore += fileResult.getScore();
                
                // Count issues by type
                for (AnalysisResponse.CodeIssue issue : fileResult.getIssues()) {
                    issuesByType.merge(issue.getCategory(), 1, Integer::sum);
                }
            }
            
            response.setResults(results);
            
            // Create summary
            summary.put("totalFiles", results.size());
            summary.put("totalIssues", totalIssues);
            summary.put("averageScore", totalScore / results.size());
            summary.put("issuesByType", issuesByType);
            response.setSummary(summary);
            
            logger.info("Batch analysis completed: {} files processed, {} total issues", 
                       results.size(), totalIssues);
            
            return response;
            
        } catch (Exception e) {
            logger.error("Error during batch analysis: {}", e.getMessage(), e);
            throw new ValidationException("Failed to analyze batch: " + e.getMessage());
        }
    }
    
    public List<AnalysisType> getAvailableAnalysisTypes() {
        return new ArrayList<>(ANALYSIS_TYPES);
    }
    
    public List<String> getSupportedLanguages() {
        return new ArrayList<>(SUPPORTED_LANGUAGES);
    }
    
    public QualityMetrics calculateQualityMetrics(String code, String language) {
        logger.info("Calculating quality metrics for language: {}", language);
        
        try {
            QualityMetrics metrics = new QualityMetrics();
            
            String[] lines = code.split("\n");
            int loc = lines.length;
            
            // Mock quality calculations
            double overallScore = calculateOverallScore(code, language);
            
            Map<String, Double> categoryScores = new HashMap<>();
            categoryScores.put("readability", 7.5);
            categoryScores.put("complexity", 8.2);
            categoryScores.put("maintainability", 7.8);
            categoryScores.put("testability", 6.9);
            
            metrics.setOverallScore(overallScore);
            metrics.setCategoryScores(categoryScores);
            metrics.setLinesOfCode(loc);
            metrics.setCyclomaticComplexity(calculateCyclomaticComplexity(code));
            metrics.setMaintainabilityIndex(calculateMaintainabilityIndex(code));
            
            metrics.setStrengths(List.of("Good naming conventions", "Proper error handling", "Clear structure"));
            metrics.setWeaknesses(List.of("Add more unit tests", "Improve documentation", "Reduce coupling"));
            
            logger.info("Quality metrics calculated: overall score {}", overallScore);
            return metrics;
            
        } catch (Exception e) {
            logger.error("Error calculating quality metrics: {}", e.getMessage(), e);
            throw new ValidationException("Failed to calculate quality metrics: " + e.getMessage());
        }
    }
    
    public SecurityScanResult performSecurityScan(String code, String language) {
        logger.info("Performing security scan for language: {}", language);
        
        try {
            SecurityScanResult result = new SecurityScanResult();
            List<SecurityVulnerability> vulnerabilities = new ArrayList<>();
            
            // Mock security analysis
            String[] lines = code.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].trim().toLowerCase();
                
                if (line.contains("password") || line.contains("secret") || line.contains("key")) {
                    vulnerabilities.add(new SecurityScanResult.SecurityVulnerability(
                        "hardcoded-credentials", "high", 
                        "Potential hardcoded credentials detected", i + 1,
                        "Use environment variables or secure configuration", "CWE-798"
                    ));
                }
                
                if (line.contains("sql") && line.contains("+") || line.contains("concat")) {
                    vulnerabilities.add(new SecurityScanResult.SecurityVulnerability(
                        "sql-injection", "critical",
                        "Potential SQL injection vulnerability", i + 1,
                        "Use parameterized queries or prepared statements", "CWE-89"
                    ));
                }
                
                if (line.contains("eval") || line.contains("exec")) {
                    vulnerabilities.add(new SecurityScanResult.SecurityVulnerability(
                        "code-injection", "high",
                        "Potential code injection vulnerability", i + 1,
                        "Avoid using eval/exec functions with user input", "CWE-94"
                    ));
                }
            }
            
            result.setVulnerabilities(vulnerabilities);
            result.setRiskScore(calculateRiskScore(vulnerabilities));
            result.setRiskLevel(determineRiskLevel(result.getRiskScore()));
            result.setHasCriticalIssues(vulnerabilities.stream().anyMatch(v -> "critical".equals(v.getSeverity())));
            result.setRecommendations(generateSecurityRecommendations(vulnerabilities));
            
            logger.info("Security scan completed: {} vulnerabilities found", vulnerabilities.size());
            return result;
            
        } catch (Exception e) {
            logger.error("Error during security scan: {}", e.getMessage(), e);
            throw new ValidationException("Failed to perform security scan: " + e.getMessage());
        }
    }
    
    public PerformanceAnalysis analyzePerformance(String code, String language) {
        logger.info("Performing performance analysis for language: {}", language);
        
        try {
            PerformanceAnalysis analysis = new PerformanceAnalysis();
            List<PerformanceIssue> issues = new ArrayList<>();
            
            String[] lines = code.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].trim();
                
                if (line.contains("for (") && line.contains("i <") && line.contains("i++")) {
                    issues.add(new PerformanceAnalysis.PerformanceIssue(
                        "inefficient-loop", "medium",
                        "Potential inefficient loop detected", i + 1,
                        "Consider using enhanced for loop or streams", "Optimize iteration"
                    ));
                }
                
                if (line.contains("SELECT *")) {
                    issues.add(new PerformanceAnalysis.PerformanceIssue(
                        "select-all", "high",
                        "SELECT * query detected", i + 1,
                        "Specify only required columns", "Database optimization"
                    ));
                }
                
                if (line.contains("new ") && line.contains("[]") && line.contains("for")) {
                    issues.add(new PerformanceAnalysis.PerformanceIssue(
                        "array-growth", "medium",
                        "Array growth in loop detected", i + 1,
                        "Pre-size array or use ArrayList with initial capacity", "Memory optimization"
                    ));
                }
            }
            
            analysis.setPerformanceIssues(issues);
            analysis.setPerformanceScore(calculatePerformanceScore(issues));
            analysis.setOptimizations(generateOptimizations(issues));
            analysis.setMetrics(generatePerformanceMetrics(code, issues));
            analysis.setBottleneck(identifyBottleneck(issues));
            
            logger.info("Performance analysis completed: {} issues found", issues.size());
            return analysis;
            
        } catch (Exception e) {
            logger.error("Error during performance analysis: {}", e.getMessage(), e);
            throw new ValidationException("Failed to analyze performance: " + e.getMessage());
        }
    }
    
    public List<AnalysisHistory> getAnalysisHistory(String userId, int limit) {
        logger.debug("Fetching analysis history for user: {}, limit: {}", userId, limit);
        
        List<AnalysisHistory> history = new ArrayList<>();
        
        // Mock history data
        for (int i = 1; i <= Math.min(limit, 5); i++) {
            AnalysisHistory record = new AnalysisHistory();
            record.setId("analysis-" + UUID.randomUUID().toString().substring(0, 8));
            record.setUserId(userId);
            record.setAnalysisType("quality");
            record.setLanguage("java");
            record.setScore(7.5 + (Math.random() * 2));
            record.setIssuesFound((int) (Math.random() * 10));
            record.setAnalyzedAt(LocalDateTime.now().minusHours(i * 2));
            record.setStatus("completed");
            history.add(record);
        }
        
        return history;
    }
    
    public ComparisonResult compareAnalyses(ComparisonRequest request) {
        logger.info("Comparing {} analyses", request.getAnalysisIds().size());
        
        try {
            ComparisonResult result = new ComparisonResult();
            result.setComparisonId(UUID.randomUUID().toString());
            result.setComparedAt(LocalDateTime.now());
            
            List<AnalysisComparison> comparisons = new ArrayList<>();
            
            // Mock comparison data
            for (String analysisId : request.getAnalysisIds()) {
                ComparisonResult.AnalysisComparison comparison = new ComparisonResult.AnalysisComparison();
                comparison.setAnalysisId(analysisId);
                comparison.setScore(7.0 + Math.random() * 3);
                comparison.setIssueCount((int) (Math.random() * 15));
                
                Map<String, Object> metrics = new HashMap<>();
                metrics.put("complexity", 5 + Math.random() * 5);
                metrics.put("maintainability", 6 + Math.random() * 4);
                comparison.setMetrics(metrics);
                
                comparisons.add(comparison);
            }
            
            // Sort by score
            comparisons.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
            
            // Assign ranks
            for (int i = 0; i < comparisons.size(); i++) {
                comparisons.get(i).setRank((i + 1) + "/" + comparisons.size());
            }
            
            result.setComparisons(comparisons);
            result.setWinner(comparisons.isEmpty() ? null : comparisons.get(0).getAnalysisId());
            
            Map<String, Object> summary = new HashMap<>();
            summary.put("totalAnalyses", comparisons.size());
            summary.put("averageScore", comparisons.stream().mapToDouble(AnalysisComparison::getScore).average().orElse(0));
            summary.put("scoreRange", Map.of(
                "min", comparisons.stream().mapToDouble(AnalysisComparison::getScore).min().orElse(0),
                "max", comparisons.stream().mapToDouble(AnalysisComparison::getScore).max().orElse(0)
            ));
            result.setSummary(summary);
            
            logger.info("Analysis comparison completed", result);
            return result;
            
        } catch (Exception e) {
            logger.error("Error during analysis comparison: {}", e.getMessage(), e);
            throw new ValidationException("Failed to compare analyses: " + e.getMessage());
        }
    }
    
    private void validateAnalysisRequest(AnalysisRequest request) {
        if (request == null) {
            throw new ValidationException("Analysis request cannot be null");
        }
        if (!SUPPORTED_LANGUAGES.contains(request.getLanguage().toLowerCase())) {
            throw new ValidationException("Unsupported language: " + request.getLanguage());
        }
        List<String> validTypes = List.of("security", "performance", "quality", "style");
        if (!validTypes.contains(request.getAnalysisType().toLowerCase())) {
            throw new ValidationException("Invalid analysis type: " + request.getAnalysisType());
        }
    }
    
    private List<AnalysisResponse.CodeIssue> analyzeCodeIssues(AnalysisRequest request) {
        List<AnalysisResponse.CodeIssue> issues = new ArrayList<>();
        String[] lines = request.getCode().split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            
            switch (request.getAnalysisType().toLowerCase()) {
                case "security":
                    issues.addAll(analyzeSecurityIssues(line, i + 1));
                    break;
                case "performance":
                    issues.addAll(analyzePerformanceIssues(line, i + 1));
                    break;
                case "quality":
                    issues.addAll(analyzeQualityIssues(line, i + 1));
                    break;
                case "style":
                    issues.addAll(analyzeStyleIssues(line, i + 1));
                    break;
            }
        }
        
        return issues;
    }
    
    private List<AnalysisResponse.CodeIssue> analyzeSecurityIssues(String line, int lineNumber) {
        List<AnalysisResponse.CodeIssue> issues = new ArrayList<>();
        
        if (line.contains("password") || line.contains("secret")) {
            issues.add(new AnalysisResponse.CodeIssue("high", "Potential hardcoded credentials", lineNumber));
        }
        
        if (line.contains("eval") || line.contains("exec")) {
            issues.add(new AnalysisResponse.CodeIssue("critical", "Potential code injection", lineNumber));
        }
        
        return issues;
    }
    
    private List<AnalysisResponse.CodeIssue> analyzePerformanceIssues(String line, int lineNumber) {
        List<AnalysisResponse.CodeIssue> issues = new ArrayList<>();
        
        if (line.contains("SELECT *")) {
            issues.add(new AnalysisResponse.CodeIssue("medium", "Inefficient SELECT * query", lineNumber));
        }
        
        if (line.contains("for (") && line.contains("size()")) {
            issues.add(new AnalysisResponse.CodeIssue("low", "Repeated size() call in loop", lineNumber));
        }
        
        return issues;
    }
    
    private List<AnalysisResponse.CodeIssue> analyzeQualityIssues(String line, int lineNumber) {
        List<AnalysisResponse.CodeIssue> issues = new ArrayList<>();
        
        if (line.length() > 120) {
            issues.add(new AnalysisResponse.CodeIssue("low", "Line too long (>120 characters)", lineNumber));
        }
        
        if (line.contains("TODO") || line.contains("FIXME")) {
            issues.add(new AnalysisResponse.CodeIssue("medium", "Unresolved TODO comment", lineNumber));
        }
        
        return issues;
    }
    
    private List<AnalysisResponse.CodeIssue> analyzeStyleIssues(String line, int lineNumber) {
        List<AnalysisResponse.CodeIssue> issues = new ArrayList<>();
        
        if (line.contains("  ") && line.contains("    ")) {
            issues.add(new AnalysisResponse.CodeIssue("low", "Inconsistent indentation", lineNumber));
        }
        
        if (line.endsWith(" ")) {
            issues.add(new AnalysisResponse.CodeIssue("low", "Trailing whitespace", lineNumber));
        }
        
        return issues;
    }
    
    private double calculateScore(List<AnalysisResponse.CodeIssue> issues, String analysisType) {
        if (issues.isEmpty()) return 10.0;
        
        double score = 10.0;
        for (AnalysisResponse.CodeIssue issue : issues) {
            switch (issue.getSeverity()) {
                case "critical" -> score -= 2.5;
                case "high" -> score -= 2.0;
                case "medium" -> score -= 1.0;
                case "low" -> score -= 0.5;
            }
        }
        
        return Math.max(0.0, score);
    }
    
    private String generateSummary(List<AnalysisResponse.CodeIssue> issues, double score, String analysisType) {
        if (issues.isEmpty()) {
            return String.format("Excellent %s analysis! No issues found. Score: %.1f/10", analysisType, score);
        }
        
        long critical = issues.stream().filter(i -> "critical".equals(i.getSeverity())).count();
        long high = issues.stream().filter(i -> "high".equals(i.getSeverity())).count();
        long medium = issues.stream().filter(i -> "medium".equals(i.getSeverity())).count();
        long low = issues.stream().filter(i -> "low".equals(i.getSeverity())).count();
        
        return String.format(
            "%s analysis complete: %d issues found (%d critical, %d high, %d medium, %d low). Score: %.1f/10",
            analysisType.substring(0, 1).toUpperCase() + analysisType.substring(1),
            issues.size(), critical, high, medium, low, score
        );
    }
    
    private List<String> generateImprovements(AnalysisRequest request) {
        return switch (request.getAnalysisType().toLowerCase()) {
            case "security" -> List.of("Use secure coding practices", "Implement input validation", "Use parameterized queries");
            case "performance" -> List.of("Optimize database queries", "Use caching strategies", "Profile bottlenecks");
            case "quality" -> List.of("Add comprehensive unit tests", "Improve code documentation", "Refactor complex methods");
            case "style" -> List.of("Follow coding standards", "Use consistent formatting", "Add meaningful comments");
            default -> List.of("Review code quality", "Add tests", "Improve documentation");
        };
    }
    
    private Map<String, Object> generateMetrics(AnalysisRequest request, List<AnalysisResponse.CodeIssue> issues, double score) {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("analysisType", request.getAnalysisType());
        metrics.put("language", request.getLanguage());
        metrics.put("score", score);
        metrics.put("issueCount", issues.size());
        metrics.put("linesAnalyzed", request.getCode().split("\n").length);
        
        Map<String, Long> severityBreakdown = new HashMap<>();
        severityBreakdown.put("critical", issues.stream().filter(i -> "critical".equals(i.getSeverity())).count());
        severityBreakdown.put("high", issues.stream().filter(i -> "high".equals(i.getSeverity())).count());
        severityBreakdown.put("medium", issues.stream().filter(i -> "medium".equals(i.getSeverity())).count());
        severityBreakdown.put("low", issues.stream().filter(i -> "low".equals(i.getSeverity())).count());
        metrics.put("severityBreakdown", severityBreakdown);
        
        return metrics;
    }
    
    private double calculateOverallScore(String code, String language) {
        // Mock calculation based on code characteristics
        double baseScore = 8.0;
        
        if (code.length() > 1000) baseScore -= 0.5;
        if (code.split("\n").length > 100) baseScore -= 0.3;
        if (code.contains("TODO") || code.contains("FIXME")) baseScore -= 0.7;
        
        return Math.max(0.0, Math.min(10.0, baseScore + Math.random()));
    }
    
    private int calculateCyclomaticComplexity(String code) {
        // Mock cyclomatic complexity calculation
        int complexity = 1;
        String[] keywords = {"if", "else", "while", "for", "case", "catch", "&&", "||"};
        
        for (String keyword : keywords) {
            complexity += (code.split(keyword).length - 1);
        }
        
        return Math.max(1, complexity / 10); // Normalize
    }
    
    private double calculateMaintainabilityIndex(String code) {
        // Mock maintainability index calculation
        int loc = code.split("\n").length;
        int complexity = calculateCyclomaticComplexity(code);
        
        // Simplified maintainability index formula
        return Math.max(0, Math.min(100, 171 - 5.2 * Math.log(complexity) - 0.23 * Math.log(loc))) / 10;
    }
    
    private int calculateRiskScore(List<SecurityVulnerability> vulnerabilities) {
        int score = 0;
        for (SecurityVulnerability vuln : vulnerabilities) {
            score += switch (vuln.getSeverity()) {
                case "critical" -> 10;
                case "high" -> 7;
                case "medium" -> 4;
                case "low" -> 1;
                default -> 0;
            };
        }
        return score;
    }
    
    private String determineRiskLevel(int riskScore) {
        if (riskScore >= 20) return "critical";
        if (riskScore >= 10) return "high";
        if (riskScore >= 5) return "medium";
        return "low";
    }
    
    private List<String> generateSecurityRecommendations(List<SecurityVulnerability> vulnerabilities) {
        List<String> recommendations = new ArrayList<>();
        
        if (vulnerabilities.stream().anyMatch(v -> "hardcoded-credentials".equals(v.getType()))) {
            recommendations.add("Store credentials in environment variables or secure vault");
        }
        
        if (vulnerabilities.stream().anyMatch(v -> "sql-injection".equals(v.getType()))) {
            recommendations.add("Use parameterized queries to prevent SQL injection");
        }
        
        if (vulnerabilities.stream().anyMatch(v -> "code-injection".equals(v.getType()))) {
            recommendations.add("Avoid dynamic code execution with user input");
        }
        
        recommendations.add("Conduct regular security audits");
        recommendations.add("Implement input validation and sanitization");
        recommendations.add("Use security scanning tools in CI/CD pipeline");
        
        return recommendations;
    }
    
    private double calculatePerformanceScore(List<PerformanceIssue> issues) {
        if (issues.isEmpty()) return 10.0;
        
        double score = 10.0;
        for (PerformanceIssue issue : issues) {
            score += switch (issue.getSeverity()) {
                case "critical" -> -2.0;
                case "high" -> -1.5;
                case "medium" -> -1.0;
                case "low" -> -0.5;
                default -> 0;
            };
        }
        
        return Math.max(0.0, score);
    }
    
    private List<String> generateOptimizations(List<PerformanceIssue> issues) {
        List<String> optimizations = new ArrayList<>();
        
        if (issues.stream().anyMatch(i -> "inefficient-loop".equals(i.getType()))) {
            optimizations.add("Use enhanced for loops or streams for better performance");
        }
        
        if (issues.stream().anyMatch(i -> "select-all".equals(i.getType()))) {
            optimizations.add("Specify only required columns in database queries");
        }
        
        if (issues.stream().anyMatch(i -> "array-growth".equals(i.getType()))) {
            optimizations.add("Pre-size collections to avoid repeated resizing");
        }
        
        optimizations.add("Profile application to identify bottlenecks");
        optimizations.add("Implement caching for frequently accessed data");
        optimizations.add("Use connection pooling for database operations");
        
        return optimizations;
    }
    
    private Map<String, Object> generatePerformanceMetrics(String code, List<PerformanceIssue> issues) {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalIssues", issues.size());
        metrics.put("linesOfCode", code.split("\n").length);
        metrics.put("complexity", calculateCyclomaticComplexity(code));
        
        Map<String, Long> issueTypes = new HashMap<>();
        for (PerformanceIssue issue : issues) {
            issueTypes.merge(issue.getType(), 1L, Long::sum);
        }
        metrics.put("issueTypes", issueTypes);
        
        return metrics;
    }
    
    private String identifyBottleneck(List<PerformanceIssue> issues) {
        if (issues.isEmpty()) return null;
        
        // Find the most severe performance issue
        return issues.stream()
            .filter(i -> "critical".equals(i.getSeverity()) || "high".equals(i.getSeverity()))
            .map(PerformanceIssue::getType)
            .findFirst()
            .orElse(issues.get(0).getType());
    }
}
