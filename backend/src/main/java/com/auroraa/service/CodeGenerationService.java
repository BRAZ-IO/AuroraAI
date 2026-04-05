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
        String ctx = capitalizeFirst(request.getContext());
        String ctxLower = request.getContext().toLowerCase();
        return "@RestController\n" +
            "@RequestMapping(\"/api/" + ctxLower + "\")\n" +
            "public class " + ctx + "Controller {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private " + ctx + "Service service;\n" +
            "\n" +
            "    @GetMapping\n" +
            "    public List<" + ctx + "> getAll() {\n" +
            "        return service.findAll();\n" +
            "    }\n" +
            "\n" +
            "    @GetMapping(\"/{id}\")\n" +
            "    public " + ctx + " getById(@PathVariable String id) {\n" +
            "        return service.findById(id);\n" +
            "    }\n" +
            "\n" +
            "    @PostMapping\n" +
            "    public " + ctx + " create(@RequestBody " + ctx + " request) {\n" +
            "        return service.create(request);\n" +
            "    }\n" +
            "\n" +
            "    @PutMapping(\"/{id}\")\n" +
            "    public " + ctx + " update(@PathVariable String id, @RequestBody " + ctx + " request) {\n" +
            "        return service.update(id, request);\n" +
            "    }\n" +
            "\n" +
            "    @DeleteMapping(\"/{id}\")\n" +
            "    public void delete(@PathVariable String id) {\n" +
            "        service.delete(id);\n" +
            "    }\n" +
            "}\n";
    }
    
    private String generateJavaService(CodeGenerationRequest request) {
        String ctx = capitalizeFirst(request.getContext());
        return "@Service\n" +
            "public class " + ctx + "Service {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private " + ctx + "Repository repository;\n" +
            "\n" +
            "    public List<" + ctx + "> findAll() {\n" +
            "        return repository.findAll();\n" +
            "    }\n" +
            "\n" +
            "    public Optional<" + ctx + "> findById(String id) {\n" +
            "        return repository.findById(id);\n" +
            "    }\n" +
            "\n" +
            "    public " + ctx + " create(" + ctx + " request) {\n" +
            "        " + ctx + " entity = new " + ctx + "(request);\n" +
            "        return repository.save(entity);\n" +
            "    }\n" +
            "\n" +
            "    public " + ctx + " update(String id, " + ctx + " request) {\n" +
            "        " + ctx + " existing = repository.findById(id)\n" +
            "            .orElseThrow(() -> new ResourceNotFoundException(\"" + ctx + " not found\"));\n" +
            "\n" +
            "        existing.updateFrom(request);\n" +
            "        return repository.save(existing);\n" +
            "    }\n" +
            "\n" +
            "    public void delete(String id) {\n" +
            "        if (!repository.existsById(id)) {\n" +
            "            throw new ResourceNotFoundException(\"" + ctx + " not found\");\n" +
            "        }\n" +
            "        repository.deleteById(id);\n" +
            "    }\n" +
            "}\n";
    }
    
    private String generateJavaEntity(CodeGenerationRequest request) {
        String ctx = capitalizeFirst(request.getContext());
        String ctxLower = request.getContext().toLowerCase();
        return "@Entity\n" +
            "@Table(name = \"" + ctxLower + "s\")\n" +
            "public class " + ctx + " {\n" +
            "\n" +
            "    @Id\n" +
            "    @GeneratedValue(strategy = GenerationType.AUTO)\n" +
            "    private String id;\n" +
            "\n" +
            "    @Column(nullable = false)\n" +
            "    private String name;\n" +
            "\n" +
            "    @Column(nullable = false)\n" +
            "    private LocalDateTime createdAt;\n" +
            "\n" +
            "    @Column(nullable = false)\n" +
            "    private LocalDateTime updatedAt;\n" +
            "\n" +
            "    public " + ctx + "() {\n" +
            "        this.id = UUID.randomUUID().toString();\n" +
            "        this.createdAt = LocalDateTime.now();\n" +
            "        this.updatedAt = LocalDateTime.now();\n" +
            "    }\n" +
            "\n" +
            "    // Getters and setters\n" +
            "    public String getId() { return id; }\n" +
            "    public void setId(String id) { this.id = id; }\n" +
            "\n" +
            "    public String getName() { return name; }\n" +
            "    public void setName(String name) {\n" +
            "        this.name = name;\n" +
            "        this.updatedAt = LocalDateTime.now();\n" +
            "    }\n" +
            "\n" +
            "    public LocalDateTime getCreatedAt() { return createdAt; }\n" +
            "    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }\n" +
            "\n" +
            "    public LocalDateTime getUpdatedAt() { return updatedAt; }\n" +
            "    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }\n" +
            "}\n";
    }
    
    private String generateReactComponent(CodeGenerationRequest request) {
        String ctx = capitalizeFirst(request.getContext());
        String ctxLower = request.getContext().toLowerCase();
        return "import React, { useState, useEffect } from 'react';\n" +
            "import './" + ctx + ".css';\n" +
            "\n" +
            "const " + ctx + " = () => {\n" +
            "    const [data, setData] = useState([]);\n" +
            "    const [loading, setLoading] = useState(true);\n" +
            "\n" +
            "    useEffect(() => {\n" +
            "        fetchData();\n" +
            "    }, []);\n" +
            "\n" +
            "    const fetchData = async () => {\n" +
            "        try {\n" +
            "            setLoading(true);\n" +
            "            const response = await fetch('/api/" + ctxLower + "');\n" +
            "            const result = await response.json();\n" +
            "            setData(result);\n" +
            "        } catch (error) {\n" +
            "            console.error('Error fetching data:', error);\n" +
            "        } finally {\n" +
            "            setLoading(false);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    if (loading) {\n" +
            "        return <div>Loading...</div>;\n" +
            "    }\n" +
            "\n" +
            "    return (\n" +
            "        <div className=\"" + ctxLower + "-container\">\n" +
            "            <h1>" + ctx + "</h1>\n" +
            "            <div className=\"" + ctxLower + "-list\">\n" +
            "                {data.map(item => (\n" +
            "                    <div key={item.id} className=\"" + ctxLower + "-item\">\n" +
            "                        <h3>{item.name}</h3>\n" +
            "                        <p>Created: {new Date(item.createdAt).toLocaleDateString()}</p>\n" +
            "                    </div>\n" +
            "                ))}\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    );\n" +
            "};\n" +
            "\n" +
            "export default " + ctx + ";\n";
    }
    
    private String generateJavaScriptFunction(CodeGenerationRequest request) {
        String ctx = request.getContext().toLowerCase();
        String prompt = request.getPrompt();
        return "/**\n" +
            " * " + prompt + "\n" +
            " * @param {Object} params - Function parameters\n" +
            " * @returns {Promise<Object>} - Function result\n" +
            " */\n" +
            "const " + ctx + " = async (params = {}) => {\n" +
            "    try {\n" +
            "        // Validate input parameters\n" +
            "        if (!params || typeof params !== 'object') {\n" +
            "            throw new Error('Invalid parameters provided');\n" +
            "        }\n" +
            "\n" +
            "        // Main logic here\n" +
            "        const result = {\n" +
            "            success: true,\n" +
            "            data: params,\n" +
            "            timestamp: new Date().toISOString()\n" +
            "        };\n" +
            "\n" +
            "        return result;\n" +
            "\n" +
            "    } catch (error) {\n" +
            "        console.error('Error in " + ctx + ":', error);\n" +
            "        throw error;\n" +
            "    }\n" +
            "};\n" +
            "\n" +
            "module.exports = " + ctx + ";\n";
    }
    
    private String generateGenericCode(CodeGenerationRequest request) {
        String lang = request.getLanguage();
        String prompt = request.getPrompt();
        String framework = request.getFramework();
        String ctx = request.getContext();
        return "// Generated code for " + lang + "\n" +
            "// Prompt: " + prompt + "\n" +
            "// Framework: " + framework + "\n" +
            "// Context: " + ctx + "\n" +
            "\n" +
            "// TODO: Implement specific logic based on requirements\n" +
            "// This is a template that should be customized\n" +
            "\n" +
            "function " + ctx.toLowerCase() + "(params) {\n" +
            "    // Add your implementation here\n" +
            "    console.log('" + ctx + " called with:', params);\n" +
            "\n" +
            "    return {\n" +
            "        status: 'success',\n" +
            "        message: '" + ctx + " executed successfully',\n" +
            "        data: params\n" +
            "    };\n" +
            "}\n" +
            "\n" +
            "module.exports = " + ctx.toLowerCase() + ";\n";
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
