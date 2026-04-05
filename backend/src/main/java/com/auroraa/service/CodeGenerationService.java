package com.auroraa.service;

import com.auroraa.dto.CodeAnalysisRequest;
import com.auroraa.dto.CodeAnalysisResponse;
import com.auroraa.dto.CodeGenerationRequest;
import com.auroraa.dto.CodeGenerationResponse;
import com.auroraa.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CodeGenerationService {
    
    private static final Logger logger = LoggerFactory.getLogger(CodeGenerationService.class);
    
    private static final Map<String, List<String>> SUPPORTED_LANGUAGES = Map.of(
        "java", List.of("spring-boot", "spring-mvc", "jakarta-ee"),
        "javascript", List.of("react", "vue", "angular", "express", "node"),
        "python", List.of("django", "flask", "fastapi", "pandas"),
        "typescript", List.of("react", "vue", "angular", "express", "node"),
        "go", List.of("gin", "echo", "fiber"),
        "rust", List.of("actix", "rocket", "warp")
    );
    
    private static final Map<String, List<String>> CODE_TEMPLATES = Map.of(
        "java", List.of("rest-controller", "service", "repository", "entity", "dto"),
        "javascript", List.of("react-component", "express-server", "utility-function", "class"),
        "python", List.of("flask-app", "django-view", "data-class", "function"),
        "typescript", List.of("react-component", "express-server", "interface", "class"),
        "go", List.of("handler", "struct", "interface", "package"),
        "rust", List.of("struct", "trait", "function", "module")
    );
    
    public CodeGenerationResponse generateCode(CodeGenerationRequest request) {
        logger.info("Starting code generation for language: {}, framework: {}", 
                   request.getLanguage(), request.getFramework());
        
        validateCodeGenerationRequest(request);
        
        long startTime = System.currentTimeMillis();
        
        try {
            String generatedCode = generateMockCode(request);
            long processingTime = System.currentTimeMillis() - startTime;
            
            CodeGenerationResponse response = new CodeGenerationResponse(
                UUID.randomUUID().toString(),
                generatedCode,
                request.getLanguage()
            );
            
            response.setExplanation(generateExplanation(request));
            response.setSuggestions(generateSuggestions(request));
            response.setProcessingTimeMs((int) processingTime);
            
            logger.info("Code generation completed in {}ms", processingTime);
            return response;
            
        } catch (Exception e) {
            logger.error("Error during code generation: {}", e.getMessage(), e);
            throw new ValidationException("Failed to generate code: " + e.getMessage());
        }
    }
    
    public CodeAnalysisResponse analyzeCode(CodeAnalysisRequest request) {
        logger.info("Starting code analysis for language: {}, type: {}", 
                   request.getLanguage(), request.getAnalysisType());
        
        validateCodeAnalysisRequest(request);
        
        try {
            List<CodeAnalysisResponse.CodeIssue> issues = analyzeMockIssues(request);
            double score = calculateScore(issues, request.getAnalysisType());
            
            CodeAnalysisResponse response = new CodeAnalysisResponse(
                UUID.randomUUID().toString(),
                issues,
                score
            );
            
            response.setSummary(generateSummary(issues, score));
            response.setImprovements(generateImprovements(request));
            response.setLinesAnalyzed(request.getCode().split("\n").length);
            response.setAnalysisType(request.getAnalysisType());
            
            logger.info("Code analysis completed with score: {}, issues: {}", score, issues.size());
            return response;
            
        } catch (Exception e) {
            logger.error("Error during code analysis: {}", e.getMessage(), e);
            throw new ValidationException("Failed to analyze code: " + e.getMessage());
        }
    }
    
    public List<String> getSupportedLanguages() {
        return new ArrayList<>(SUPPORTED_LANGUAGES.keySet());
    }
    
    public List<String> getFrameworksForLanguage(String language) {
        if (!SUPPORTED_LANGUAGES.containsKey(language)) {
            logger.warn("Unsupported language requested: {}", language);
            return Collections.emptyList();
        }
        return SUPPORTED_LANGUAGES.get(language);
    }
    
    public List<String> getCodeTemplates(String language) {
        if (!CODE_TEMPLATES.containsKey(language)) {
            logger.warn("No templates available for language: {}", language);
            return Collections.emptyList();
        }
        return CODE_TEMPLATES.get(language);
    }
    
    private void validateCodeGenerationRequest(CodeGenerationRequest request) {
        if (request == null) {
            throw new ValidationException("Code generation request cannot be null");
        }
        if (!SUPPORTED_LANGUAGES.containsKey(request.getLanguage().toLowerCase())) {
            throw new ValidationException("Unsupported language: " + request.getLanguage());
        }
        if (request.getFramework() != null && !getFrameworksForLanguage(request.getLanguage()).contains(request.getFramework())) {
            throw new ValidationException("Unsupported framework: " + request.getFramework());
        }
    }
    
    private void validateCodeAnalysisRequest(CodeAnalysisRequest request) {
        if (request == null) {
            throw new ValidationException("Code analysis request cannot be null");
        }
        if (!SUPPORTED_LANGUAGES.containsKey(request.getLanguage().toLowerCase())) {
            throw new ValidationException("Unsupported language: " + request.getLanguage());
        }
        List<String> validTypes = List.of("security", "performance", "quality", "style");
        if (!validTypes.contains(request.getAnalysisType().toLowerCase())) {
            throw new ValidationException("Invalid analysis type: " + request.getAnalysisType());
        }
    }
    
    private String generateMockCode(CodeGenerationRequest request) {
        String language = request.getLanguage().toLowerCase();
        String prompt = request.getPrompt().toLowerCase();
        
        if (language.equals("java")) {
            if (prompt.contains("controller") || prompt.contains("rest")) {
                return generateJavaController(request);
            } else if (prompt.contains("service")) {
                return generateJavaService(request);
            } else if (prompt.contains("entity")) {
                return generateJavaEntity(request);
            }
        } else if (language.equals("javascript")) {
            if (prompt.contains("component") || prompt.contains("react")) {
                return generateReactComponent(request);
            } else if (prompt.contains("function")) {
                return generateJavaScriptFunction(request);
            }
        }
        
        return generateGenericCode(request);
    }
    
    private String generateJavaController(CodeGenerationRequest request) {
        return """
            @RestController
            @RequestMapping("/api/" + request.getContext().toLowerCase() + ")
            public class """ + capitalizeFirst(request.getContext()) + """Controller {
                
                @Autowired
                private """ + capitalizeFirst(request.getContext()) + """Service service;
                
                @GetMapping
                public List<""" + capitalizeFirst(request.getContext()) + """> getAll() {
                    return service.findAll();
                }
                
                @GetMapping("/{id}")
                public """ + capitalizeFirst(request.getContext()) + """ getById(@PathVariable String id) {
                    return service.findById(id);
                }
                
                @PostMapping
                public """ + capitalizeFirst(request.getContext()) + """ create(@RequestBody """ + capitalizeFirst(request.getContext()) + """ request) {
                    return service.create(request);
                }
                
                @PutMapping("/{id}")
                public """ + capitalizeFirst(request.getContext()) + """ update(@PathVariable String id, @RequestBody """ + capitalizeFirst(request.getContext()) + """ request) {
                    return service.update(id, request);
                }
                
                @DeleteMapping("/{id}")
                public void delete(@PathVariable String id) {
                    service.delete(id);
                }
            }
            """;
    }
    
    private String generateJavaService(CodeGenerationRequest request) {
        return """
            @Service
            public class """ + capitalizeFirst(request.getContext()) + """Service {
                
                @Autowired
                private """ + capitalizeFirst(request.getContext()) + """Repository repository;
                
                public List<""" + capitalizeFirst(request.getContext()) + """> findAll() {
                    return repository.findAll();
                }
                
                public Optional<""" + capitalizeFirst(request.getContext()) + """> findById(String id) {
                    return repository.findById(id);
                }
                
                public """ + capitalizeFirst(request.getContext()) + """ create(""" + capitalizeFirst(request.getContext()) + """ request) {
                    """ + capitalizeFirst(request.getContext()) + """ entity = new """ + capitalizeFirst(request.getContext()) + """(request);
                    return repository.save(entity);
                }
                
                public """ + capitalizeFirst(request.getContext()) + """ update(String id, """ + capitalizeFirst(request.getContext()) + """ request) {
                    """ + capitalizeFirst(request.getContext()) + """ existing = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("""" + capitalizeFirst(request.getContext()) + """ not found"));
                    
                    existing.updateFrom(request);
                    return repository.save(existing);
                }
                
                public void delete(String id) {
                    if (!repository.existsById(id)) {
                        throw new ResourceNotFoundException("""" + capitalizeFirst(request.getContext()) + """ not found");
                    }
                    repository.deleteById(id);
                }
            }
            """;
    }
    
    private String generateJavaEntity(CodeGenerationRequest request) {
        return """
            @Entity
            @Table(name = \"""" + request.getContext().toLowerCase() + """s")
            public class """ + capitalizeFirst(request.getContext()) + """ {
                
                @Id
                @GeneratedValue(strategy = GenerationType.AUTO)
                private String id;
                
                @Column(nullable = false)
                private String name;
                
                @Column(nullable = false)
                private LocalDateTime createdAt;
                
                @Column(nullable = false)
                private LocalDateTime updatedAt;
                
                public """ + capitalizeFirst(request.getContext()) + """() {
                    this.id = UUID.randomUUID().toString();
                    this.createdAt = LocalDateTime.now();
                    this.updatedAt = LocalDateTime.now();
                }
                
                // Getters and setters
                public String getId() { return id; }
                public void setId(String id) { this.id = id; }
                
                public String getName() { return name; }
                public void setName(String name) { 
                    this.name = name;
                    this.updatedAt = LocalDateTime.now();
                }
                
                public LocalDateTime getCreatedAt() { return createdAt; }
                public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
                
                public LocalDateTime getUpdatedAt() { return updatedAt; }
                public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
            }
            """;
    }
    
    private String generateReactComponent(CodeGenerationRequest request) {
        return """
            import React, { useState, useEffect } from 'react';
            import './""" + capitalizeFirst(request.getContext()) + """.css';
            
            const """ + capitalizeFirst(request.getContext()) + """ = () => {
                const [data, setData] = useState([]);
                const [loading, setLoading] = useState(true);
                
                useEffect(() => {
                    fetchData();
                }, []);
                
                const fetchData = async () => {
                    try {
                        setLoading(true);
                        const response = await fetch('/api/""" + request.getContext().toLowerCase() + """');
                        const result = await response.json();
                        setData(result);
                    } catch (error) {
                        console.error('Error fetching data:', error);
                    } finally {
                        setLoading(false);
                    }
                };
                
                if (loading) {
                    return <div>Loading...</div>;
                }
                
                return (
                    <div className={"""" + request.getContext().toLowerCase() + "-container"""}>
                        <h1>""" + capitalizeFirst(request.getContext()) + """</h1>
                        <div className={"""" + request.getContext().toLowerCase() + "-list"""}>
                            {data.map(item => (
                                <div key={item.id} className={"""" + request.getContext().toLowerCase() + "-item"""}>
                                    <h3>{item.name}</h3>
                                    <p>Created: {new Date(item.createdAt).toLocaleDateString()}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                );
            };
            
            export default """ + capitalizeFirst(request.getContext()) + """;
            """;
    }
    
    private String generateJavaScriptFunction(CodeGenerationRequest request) {
        return """
            /**
             * """ + request.getPrompt() + """
             * @param {Object} params - Function parameters
             * @returns {Promise<Object>} - Function result
             */
            const """ + request.getContext().toLowerCase() + """ = async (params = {}) => {
                try {
                    // Validate input parameters
                    if (!params || typeof params !== 'object') {
                        throw new Error('Invalid parameters provided');
                    }
                    
                    // Main logic here
                    const result = {
                        success: true,
                        data: params,
                        timestamp: new Date().toISOString()
                    };
                    
                    return result;
                    
                } catch (error) {
                    console.error('Error in """ + request.getContext().toLowerCase() + """:', error);
                    throw error;
                }
            };
            
            module.exports = """ + request.getContext().toLowerCase() + """;
            """;
    }
    
    private String generateGenericCode(CodeGenerationRequest request) {
        return """
            // Generated code for """ + request.getLanguage() + """
            // Prompt: """ + request.getPrompt() + """
            // Framework: """ + request.getFramework() + """
            // Context: """ + request.getContext() + """
            
            // TODO: Implement specific logic based on requirements
            // This is a template that should be customized
            
            function """ + request.getContext().toLowerCase() + """(params) {
                // Add your implementation here
                console.log('""" + request.getContext() + """ called with:', params);
                
                return {
                    status: 'success',
                    message: '""" + request.getContext() + """ executed successfully',
                    data: params
                };
            }
            
            module.exports = """ + request.getContext().toLowerCase() + """;
            """;
    }
    
    private String generateExplanation(CodeGenerationRequest request) {
        return String.format(
            "Generated %s code for %s%s using %s style. " +
            "The code follows best practices and includes proper error handling.",
            request.getLanguage(),
            request.getContext() != null ? request.getContext() : "the requested functionality",
            request.getFramework() != null ? " with " + request.getFramework() : "",
            request.getStyle()
        );
    }
    
    private List<String> generateSuggestions(CodeGenerationRequest request) {
        List<String> suggestions = new ArrayList<>();
        suggestions.add("Add comprehensive unit tests");
        suggestions.add("Implement proper error handling");
        suggestions.add("Add input validation");
        suggestions.add("Consider adding caching for better performance");
        suggestions.add("Add logging for debugging and monitoring");
        return suggestions;
    }
    
    private List<CodeAnalysisResponse.CodeIssue> analyzeMockIssues(CodeAnalysisRequest request) {
        List<CodeAnalysisResponse.CodeIssue> issues = new ArrayList<>();
        String[] lines = request.getCode().split("\n");
        
        // Mock analysis based on code content
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            
            if (request.getAnalysisType().equals("security")) {
                if (line.contains("password") || line.contains("secret") || line.contains("key")) {
                    issues.add(new CodeAnalysisResponse.CodeIssue(
                        "high", 
                        "Potential security vulnerability: hardcoded credentials", 
                        i + 1
                    ));
                }
            } else if (request.getAnalysisType().equals("performance")) {
                if (line.contains("SELECT *") || line.contains("for (let i = 0; i <")) {
                    issues.add(new CodeAnalysisResponse.CodeIssue(
                        "medium", 
                        "Potential performance issue detected", 
                        i + 1
                    ));
                }
            } else if (request.getAnalysisType().equals("style")) {
                if (line.length() > 100) {
                    issues.add(new CodeAnalysisResponse.CodeIssue(
                        "low", 
                        "Line too long (>100 characters)", 
                        i + 1
                    ));
                }
            }
        }
        
        return issues;
    }
    
    private double calculateScore(List<CodeAnalysisResponse.CodeIssue> issues, String analysisType) {
        if (issues.isEmpty()) return 10.0;
        
        double score = 10.0;
        for (CodeAnalysisResponse.CodeIssue issue : issues) {
            switch (issue.getSeverity()) {
                case "critical" -> score -= 2.0;
                case "high" -> score -= 1.5;
                case "medium" -> score -= 1.0;
                case "low" -> score -= 0.5;
            }
        }
        
        return Math.max(0.0, score);
    }
    
    private String generateSummary(List<CodeAnalysisResponse.CodeIssue> issues, double score) {
        if (issues.isEmpty()) {
            return "Excellent code quality! No issues found.";
        }
        
        long critical = issues.stream().filter(i -> "critical".equals(i.getSeverity())).count();
        long high = issues.stream().filter(i -> "high".equals(i.getSeverity())).count();
        long medium = issues.stream().filter(i -> "medium".equals(i.getSeverity())).count();
        long low = issues.stream().filter(i -> "low".equals(i.getSeverity())).count();
        
        return String.format(
            "Analysis complete: %d issues found (%d critical, %d high, %d medium, %d low). Score: %.1f/10",
            issues.size(), critical, high, medium, low, score
        );
    }
    
    private List<String> generateImprovements(CodeAnalysisRequest request) {
        List<String> improvements = new ArrayList<>();
        improvements.add("Add comprehensive error handling");
        improvements.add("Implement input validation");
        improvements.add("Add unit tests for better coverage");
        improvements.add("Consider adding code comments for better maintainability");
        improvements.add("Review and optimize algorithm complexity");
        return improvements;
    }
    
    private String capitalizeFirst(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
