package com.auroraa.controller;

import com.auroraa.dto.CodeGenerationRequest;
import com.auroraa.dto.CodeGenerationResponse;
import com.auroraa.dto.CodeAnalysisRequest;
import com.auroraa.dto.CodeAnalysisResponse;
import com.auroraa.service.CodeGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = "*")
public class CodeController {
    
    private static final Logger logger = LoggerFactory.getLogger(CodeController.class);
    
    @Autowired
    private CodeGenerationService codeGenerationService;
    
    @PostMapping("/generate")
    public ResponseEntity<CodeGenerationResponse> generateCode(@RequestBody CodeGenerationRequest request) {
        logger.info("Received code generation request for language: {}, framework: {}", 
                   request.getLanguage(), request.getFramework());
        
        try {
            CodeGenerationResponse response = codeGenerationService.generateCode(request);
            logger.info("Code generated successfully with {} characters", 
                       response.getGeneratedCode().length());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error generating code: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/analyze")
    public ResponseEntity<CodeAnalysisResponse> analyzeCode(@RequestBody CodeAnalysisRequest request) {
        logger.info("Received code analysis request for language: {}, analysis type: {}", 
                   request.getLanguage(), request.getAnalysisType());
        
        try {
            CodeAnalysisResponse response = codeGenerationService.analyzeCode(request);
            logger.info("Code analysis completed with score: {}, issues found: {}", 
                       response.getScore(), response.getIssues().size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error analyzing code: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/languages")
    public ResponseEntity<List<String>> getSupportedLanguages() {
        logger.debug("Fetching supported programming languages");
        
        try {
            List<String> languages = codeGenerationService.getSupportedLanguages();
            logger.info("Retrieved {} supported languages", languages.size());
            return ResponseEntity.ok(languages);
        } catch (Exception e) {
            logger.error("Error fetching supported languages: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/frameworks/{language}")
    public ResponseEntity<List<String>> getFrameworksForLanguage(@PathVariable String language) {
        logger.debug("Fetching frameworks for language: {}", language);
        
        try {
            List<String> frameworks = codeGenerationService.getFrameworksForLanguage(language);
            logger.info("Retrieved {} frameworks for language: {}", frameworks.size(), language);
            return ResponseEntity.ok(frameworks);
        } catch (Exception e) {
            logger.error("Error fetching frameworks for language {}: {}", language, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/templates/{language}")
    public ResponseEntity<List<String>> getCodeTemplates(@PathVariable String language) {
        logger.debug("Fetching code templates for language: {}", language);
        
        try {
            List<String> templates = codeGenerationService.getCodeTemplates(language);
            logger.info("Retrieved {} templates for language: {}", templates.size(), language);
            return ResponseEntity.ok(templates);
        } catch (Exception e) {
            logger.error("Error fetching templates for language {}: {}", language, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
