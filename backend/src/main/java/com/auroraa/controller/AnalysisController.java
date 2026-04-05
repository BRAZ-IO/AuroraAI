package com.auroraa.controller;

import com.auroraa.dto.*;
import com.auroraa.service.CodeAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "*")
public class AnalysisController {
    
    private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);
    
    @Autowired
    private CodeAnalysisService codeAnalysisService;
    
    @PostMapping("/analyze")
    @ResponseStatus(HttpStatus.CREATED)
    public AnalysisResponse analyzeCode(@RequestBody AnalysisRequest request) {
        logger.info("Starting code analysis for language: {}, type: {}", 
                   request.getLanguage(), request.getAnalysisType());
        
        try {
            AnalysisResponse response = codeAnalysisService.analyzeCode(request);
            logger.info("Code analysis completed with score: {}, issues: {}", 
                       response.getScore(), response.getIssues().size());
            return response;
        } catch (Exception e) {
            logger.error("Error during code analysis: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/batch")
    public ResponseEntity<BatchAnalysisResponse> analyzeBatch(@RequestBody BatchAnalysisRequest request) {
        logger.info("Starting batch analysis for {} files", request.getFiles().size());
        
        try {
            BatchAnalysisResponse response = codeAnalysisService.analyzeBatch(request);
            logger.info("Batch analysis completed: {} files processed", response.getResults().size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during batch analysis: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/types")
    public ResponseEntity<List<AnalysisType>> getAvailableAnalysisTypes() {
        logger.debug("Fetching available analysis types");
        
        try {
            List<AnalysisType> types = codeAnalysisService.getAvailableAnalysisTypes();
            logger.info("Retrieved {} analysis types", types.size());
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            logger.error("Error fetching analysis types: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/languages")
    public ResponseEntity<List<String>> getSupportedLanguages() {
        logger.debug("Fetching supported analysis languages");
        
        try {
            List<String> languages = codeAnalysisService.getSupportedLanguages();
            logger.info("Retrieved {} supported languages", languages.size());
            return ResponseEntity.ok(languages);
        } catch (Exception e) {
            logger.error("Error fetching supported languages: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/quality-metrics")
    public ResponseEntity<QualityMetrics> calculateQualityMetrics(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String language = request.get("language");
        
        logger.info("Calculating quality metrics for language: {}", language);
        
        try {
            QualityMetrics metrics = codeAnalysisService.calculateQualityMetrics(code, language);
            logger.info("Quality metrics calculated: overall score {}", metrics.getOverallScore());
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            logger.error("Error calculating quality metrics: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/security-scan")
    public ResponseEntity<SecurityScanResult> performSecurityScan(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String language = request.get("language");
        
        logger.info("Performing security scan for language: {}", language);
        
        try {
            SecurityScanResult result = codeAnalysisService.performSecurityScan(code, language);
            logger.info("Security scan completed: {} vulnerabilities found", result.getVulnerabilities().size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during security scan: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/performance-analysis")
    public ResponseEntity<PerformanceAnalysis> analyzePerformance(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        String language = request.get("language");
        
        logger.info("Performing performance analysis for language: {}", language);
        
        try {
            PerformanceAnalysis analysis = codeAnalysisService.analyzePerformance(code, language);
            logger.info("Performance analysis completed: {} issues found", analysis.getPerformanceIssues().size());
            return ResponseEntity.ok(analysis);
        } catch (Exception e) {
            logger.error("Error during performance analysis: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<AnalysisHistory>> getAnalysisHistory(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit) {
        logger.debug("Fetching analysis history for user: {}, limit: {}", userId, limit);
        
        try {
            List<AnalysisHistory> history = codeAnalysisService.getAnalysisHistory(userId, limit);
            logger.info("Retrieved {} analysis records for user: {}", history.size(), userId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            logger.error("Error fetching analysis history: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/compare")
    public ResponseEntity<ComparisonResult> compareAnalyses(@RequestBody ComparisonRequest request) {
        logger.info("Comparing {} analyses", request.getAnalysisIds().size());
        
        try {
            ComparisonResult result = codeAnalysisService.compareAnalyses(request);
            logger.info("Analysis comparison completed", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during analysis comparison: {}", e.getMessage(), e);
            throw e;
        }
    }
}
