package com.auroraa.controller;

import com.auroraa.dto.*;
import com.auroraa.service.DocumentationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documentation")
@CrossOrigin(origins = "*")
public class DocumentationController {
    
    private static final Logger logger = LoggerFactory.getLogger(DocumentationController.class);
    
    @Autowired
    private DocumentationService documentationService;
    
    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentationResponse generateDocumentation(@RequestBody DocumentationRequest request) {
        logger.info("Generating documentation for repository: {}, template: {}", 
                   request.getRepositoryUrl(), request.getTemplate());
        
        try {
            DocumentationResponse response = documentationService.generateDocumentation(request);
            logger.info("Documentation generated successfully with {} words", response.getWordCount());
            return response;
        } catch (Exception e) {
            logger.error("Error generating documentation: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/templates")
    public ResponseEntity<List<DocumentationTemplate>> getAvailableTemplates() {
        logger.debug("Fetching available documentation templates");
        
        try {
            List<DocumentationTemplate> templates = documentationService.getAvailableTemplates();
            logger.info("Retrieved {} documentation templates", templates.size());
            return ResponseEntity.ok(templates);
        } catch (Exception e) {
            logger.error("Error fetching templates: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/formats")
    public ResponseEntity<List<String>> getSupportedFormats() {
        logger.debug("Fetching supported documentation formats");
        
        try {
            List<String> formats = documentationService.getSupportedFormats();
            logger.info("Retrieved {} supported formats: {}", formats.size(), formats);
            return ResponseEntity.ok(formats);
        } catch (Exception e) {
            logger.error("Error fetching formats: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/analyze")
    public ResponseEntity<ProjectAnalysis> analyzeProject(@RequestBody Map<String, String> request) {
        String repositoryUrl = request.get("repositoryUrl");
        logger.info("Analyzing project repository: {}", repositoryUrl);
        
        try {
            ProjectAnalysis analysis = documentationService.analyzeProject(repositoryUrl);
            logger.info("Project analysis completed: {} files, {} technologies found", 
                       analysis.getTotalFiles(), analysis.getTechnologies().size());
            return ResponseEntity.ok(analysis);
        } catch (Exception e) {
            logger.error("Error analyzing project: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/api-docs")
    public ResponseEntity<ApiDocumentation> generateApiDocumentation(@RequestBody Map<String, Object> request) {
        String baseUrl = (String) request.get("baseUrl");
        String packageScan = (String) request.get("packageScan");
        
        logger.info("Generating API documentation for base URL: {}, package: {}", baseUrl, packageScan);
        
        try {
            ApiDocumentation apiDocs = documentationService.generateApiDocumentation(baseUrl, packageScan);
            logger.info("API documentation generated: {} endpoints documented", apiDocs.getEndpoints().size());
            return ResponseEntity.ok(apiDocs);
        } catch (Exception e) {
            logger.error("Error generating API documentation: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/preview/{template}")
    public ResponseEntity<String> previewTemplate(@PathVariable String template) {
        logger.debug("Previewing template: {}", template);
        
        try {
            String preview = documentationService.previewTemplate(template);
            logger.info("Template preview generated for: {}", template);
            return ResponseEntity.ok(preview);
        } catch (Exception e) {
            logger.error("Error previewing template {}: {}", template, e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/export")
    public ResponseEntity<byte[]> exportDocumentation(@RequestBody DocumentationExportRequest request) {
        logger.info("Exporting documentation in format: {}", request.getFormat());
        
        try {
            byte[] exportedData = documentationService.exportDocumentation(request);
            
            String contentType = switch (request.getFormat().toLowerCase()) {
                case "pdf" -> "application/pdf";
                case "html" -> "text/html";
                case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                default -> "text/markdown";
            };
            
            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"documentation." + request.getFormat() + "\"")
                .header("Content-Type", contentType)
                .body(exportedData);
                
        } catch (Exception e) {
            logger.error("Error exporting documentation: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<DocumentationHistory>> getDocumentationHistory(
            @RequestParam(defaultValue = "10") int limit) {
        logger.debug("Fetching documentation generation history, limit: {}", limit);
        
        try {
            List<DocumentationHistory> history = documentationService.getDocumentationHistory(limit);
            logger.info("Retrieved {} documentation history records", history.size());
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            logger.error("Error fetching documentation history: {}", e.getMessage(), e);
            throw e;
        }
    }
}
