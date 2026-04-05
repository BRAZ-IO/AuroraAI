package com.auroraa.service;

import com.auroraa.dto.*;
import com.auroraa.dto.ApiDocumentation.ApiEndpoint;
import com.auroraa.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DocumentationService {
    
    private static final Logger logger = LoggerFactory.getLogger(DocumentationService.class);
    
    private static final Map<String, DocumentationTemplate> TEMPLATES = Map.of(
        "standard-readme", new DocumentationTemplate("standard-readme", "Standard README", "Complete project README with all sections", "general"),
        "api-docs", new DocumentationTemplate("api-docs", "API Documentation", "REST API documentation with endpoints", "api"),
        "technical-docs", new DocumentationTemplate("technical-docs", "Technical Documentation", "In-depth technical documentation", "technical"),
        "user-guide", new DocumentationTemplate("user-guide", "User Guide", "End-user documentation", "user"),
        "changelog", new DocumentationTemplate("changelog", "Changelog", "Version history and changes", "maintenance")
    );
    
    private static final List<String> SUPPORTED_FORMATS = List.of("markdown", "html", "pdf", "docx");
    
    public DocumentationResponse generateDocumentation(DocumentationRequest request) {
        logger.info("Generating documentation using template: {} for repository: {}", 
                   request.getTemplate(), request.getRepositoryUrl());
        
        validateDocumentationRequest(request);
        
        try {
            String content = generateDocumentationContent(request);
            
            DocumentationResponse response = new DocumentationResponse(
                UUID.randomUUID().toString(),
                content,
                "markdown"
            );
            
            response.setRepositoryUrl(request.getRepositoryUrl());
            response.setTemplate(request.getTemplate());
            response.setMessage("Documentation generated successfully");
            
            logger.info("Documentation generated with {} words", response.getWordCount());
            return response;
            
        } catch (Exception e) {
            logger.error("Error generating documentation: {}", e.getMessage(), e);
            throw new ValidationException("Failed to generate documentation: " + e.getMessage());
        }
    }
    
    public List<DocumentationTemplate> getAvailableTemplates() {
        return new ArrayList<>(TEMPLATES.values());
    }
    
    public List<String> getSupportedFormats() {
        return new ArrayList<>(SUPPORTED_FORMATS);
    }
    
    public ProjectAnalysis analyzeProject(String repositoryUrl) {
        logger.info("Analyzing project repository: {}", repositoryUrl);
        
        validateRepositoryUrl(repositoryUrl);
        
        try {
            ProjectAnalysis analysis = new ProjectAnalysis();
            analysis.setRepositoryUrl(repositoryUrl);
            analysis.setTotalFiles(42);
            analysis.setLanguages(List.of("Java", "JavaScript", "CSS", "HTML"));
            analysis.setTechnologies(List.of("Spring Boot", "React", "Maven", "H2"));
            
            Map<String, Integer> fileTypes = new HashMap<>();
            fileTypes.put("Java", 15);
            fileTypes.put("JavaScript", 12);
            fileTypes.put("CSS", 8);
            fileTypes.put("HTML", 5);
            fileTypes.put("Other", 2);
            analysis.setFileTypes(fileTypes);
            
            analysis.setProjectType("Full Stack Web Application");
            analysis.setHasTests(true);
            analysis.setHasDocumentation(true);
            
            logger.info("Project analysis completed: {} files, {} technologies", 
                       analysis.getTotalFiles(), analysis.getTechnologies().size());
            
            return analysis;
            
        } catch (Exception e) {
            logger.error("Error analyzing project: {}", e.getMessage(), e);
            throw new ValidationException("Failed to analyze project: " + e.getMessage());
        }
    }
    
    public ApiDocumentation generateApiDocumentation(String baseUrl, String packageScan) {
        logger.info("Generating API documentation for base URL: {}, package: {}", baseUrl, packageScan);
        
        try {
            ApiDocumentation apiDocs = new ApiDocumentation();
            apiDocs.setTitle("AuroraAI API Documentation");
            apiDocs.setVersion("1.0.0");
            apiDocs.setBaseUrl(baseUrl);
            apiDocs.setDescription("RESTful API for AuroraAI development assistant platform");
            
            List<ApiEndpoint> endpoints = new ArrayList<>();
            
            // Chat endpoints
            endpoints.add(createApiEndpoint("POST", "/api/chat", "Send Message", "Send a message to AI assistant"));
            endpoints.add(createApiEndpoint("GET", "/api/chat/history/{userId}", "Get Chat History", "Retrieve paginated chat history"));
            endpoints.add(createApiEndpoint("GET", "/api/chat/conversation/{conversationId}", "Get Conversation", "Get specific conversation"));
            
            // Knowledge endpoints
            endpoints.add(createApiEndpoint("POST", "/api/knowledge", "Add Knowledge", "Add new knowledge entry"));
            endpoints.add(createApiEndpoint("GET", "/api/knowledge", "Get All Knowledge", "Retrieve all knowledge entries"));
            endpoints.add(createApiEndpoint("GET", "/api/knowledge/search", "Search Knowledge", "Search knowledge base"));
            
            // Code endpoints
            endpoints.add(createApiEndpoint("POST", "/api/code/generate", "Generate Code", "Generate code from prompt"));
            endpoints.add(createApiEndpoint("POST", "/api/code/analyze", "Analyze Code", "Analyze code quality"));
            endpoints.add(createApiEndpoint("GET", "/api/code/languages", "Get Languages", "Get supported languages"));
            
            // Documentation endpoints
            endpoints.add(createApiEndpoint("POST", "/api/documentation/generate", "Generate Documentation", "Generate project documentation"));
            endpoints.add(createApiEndpoint("GET", "/api/documentation/templates", "Get Templates", "Get available templates"));
            
            apiDocs.setEndpoints(endpoints);
            
            Map<String, Object> schemas = new HashMap<>();
            schemas.put("ChatRequest", Map.of("type", "object", "properties", Map.of("message", Map.of("type", "string"))));
            schemas.put("ChatResponse", Map.of("type", "object", "properties", Map.of("response", Map.of("type", "string"))));
            apiDocs.setSchemas(schemas);
            
            logger.info("API documentation generated: {} endpoints documented", endpoints.size());
            return apiDocs;
            
        } catch (Exception e) {
            logger.error("Error generating API documentation: {}", e.getMessage(), e);
            throw new ValidationException("Failed to generate API documentation: " + e.getMessage());
        }
    }
    
    public String previewTemplate(String template) {
        logger.debug("Previewing template: {}", template);
        
        if (!TEMPLATES.containsKey(template)) {
            throw new ValidationException("Template not found: " + template);
        }
        
        return switch (template) {
            case "standard-readme" -> generateStandardReadmePreview();
            case "api-docs" -> generateApiDocsPreview();
            case "technical-docs" -> generateTechnicalDocsPreview();
            default -> "# " + TEMPLATES.get(template).getName() + "\n\nTemplate preview content...";
        };
    }
    
    public byte[] exportDocumentation(DocumentationExportRequest request) {
        logger.info("Exporting documentation in format: {}", request.getFormat());
        
        if (!SUPPORTED_FORMATS.contains(request.getFormat().toLowerCase())) {
            throw new ValidationException("Unsupported format: " + request.getFormat());
        }
        
        try {
            // Mock export functionality
            String content = request.getContent() != null ? request.getContent() : "# Sample Documentation\n\nExported content";
            
            String format = request.getFormat().toLowerCase();
            if ("markdown".equals(format)) {
                return content.getBytes(StandardCharsets.UTF_8);
            } else if ("html".equals(format)) {
                return generateHtmlExport(content).getBytes(StandardCharsets.UTF_8);
            } else if ("pdf".equals(format)) {
                return generatePdfExport(content);
            } else if ("docx".equals(format)) {
                return generateDocxExport(content);
            } else {
                return content.getBytes(StandardCharsets.UTF_8);
            }
            
        } catch (Exception e) {
            logger.error("Error exporting documentation: {}", e.getMessage(), e);
            throw new ValidationException("Failed to export documentation: " + e.getMessage());
        }
    }
    
    public List<DocumentationHistory> getDocumentationHistory(int limit) {
        logger.debug("Fetching documentation generation history, limit: {}", limit);
        
        List<DocumentationHistory> history = new ArrayList<>();
        
        // Mock history data
        for (int i = 1; i <= Math.min(limit, 5); i++) {
            DocumentationHistory record = new DocumentationHistory();
            record.setId("doc-" + UUID.randomUUID().toString().substring(0, 8));
            record.setTemplate("standard-readme");
            record.setRepositoryUrl("https://github.com/example/project" + i);
            record.setGeneratedAt(LocalDateTime.now().minusHours(i * 2));
            record.setWordCount(1000 + (i * 100));
            record.setFormat("markdown");
            record.setStatus("completed");
            history.add(record);
        }
        
        return history;
    }
    
    private void validateDocumentationRequest(DocumentationRequest request) {
        if (request == null) {
            throw new ValidationException("Documentation request cannot be null");
        }
        if (!TEMPLATES.containsKey(request.getTemplate())) {
            throw new ValidationException("Unsupported template: " + request.getTemplate());
        }
        validateRepositoryUrl(request.getRepositoryUrl());
    }
    
    private void validateRepositoryUrl(String repositoryUrl) {
        if (repositoryUrl == null || repositoryUrl.trim().isEmpty()) {
            throw new ValidationException("Repository URL cannot be null or empty");
        }
        if (!repositoryUrl.startsWith("http://") && !repositoryUrl.startsWith("https://")) {
            throw new ValidationException("Repository URL must start with http:// or https://");
        }
    }
    
    private String generateDocumentationContent(DocumentationRequest request) {
        return switch (request.getTemplate()) {
            case "standard-readme" -> generateStandardReadme(request);
            case "api-docs" -> generateApiDocs(request);
            case "technical-docs" -> generateTechnicalDocs(request);
            case "user-guide" -> generateUserGuide(request);
            case "changelog" -> generateChangelog(request);
            default -> "# Generated Documentation\n\nTemplate: " + request.getTemplate();
        };
    }
    
    private String generateStandardReadme(DocumentationRequest request) {
        return """
            # Project Title
            
            ## Description
            
            This is a comprehensive project documentation generated automatically.
            
            ## Features
            
            - Feature 1
            - Feature 2
            - Feature 3
            
            ## Installation
            
            ```bash
            # Clone the repository
            git clone """ + request.getRepositoryUrl() + """
            cd project-name
            
            # Install dependencies
            npm install  # or mvn install for Java projects
            
            # Run the project
            npm start  # or mvn spring-boot:run
            ```
            
            ## Usage
            
            Detailed usage instructions...
            
            ## API Documentation
            
            See the [API Documentation](#api-docs) section for detailed API information.
            
            ## Contributing
            
            Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct.
            
            ## License
            
            This project is licensed under the MIT License.
            
            ---
            
            *This documentation was generated using AuroraAI on """ + LocalDateTime.now().toLocalDate() + "*";
    }
    
    private String generateApiDocs(DocumentationRequest request) {
        return """
            # API Documentation
            
            ## Overview
            
            This API provides access to the AuroraAI development assistant platform.
            
            ## Base URL
            
            ```
            https://api.aurorai.com
            ```
            
            ## Authentication
            
            API requests are authenticated using Bearer tokens.
            
            ## Endpoints
            
            ### Chat Endpoints
            
            #### Send Message
            ```http
            POST /api/chat
            Content-Type: application/json
            Authorization: Bearer {token}
            
            {
              "message": "How do I implement pagination?",
              "userId": "user123",
              "conversationId": "conv-456"
            }
            ```
            
            #### Get Chat History
            ```http
            GET /api/chat/history/{userId}?page=0&size=20
            Authorization: Bearer {token}
            ```
            
            ### Knowledge Endpoints
            
            #### Add Knowledge
            ```http
            POST /api/knowledge
            Content-Type: application/json
            Authorization: Bearer {token}
            
            {
              "topic": "Spring Boot Pagination",
              "response": "To implement pagination...",
              "category": "Backend"
            }
            ```
            
            ### Code Endpoints
            
            #### Generate Code
            ```http
            POST /api/code/generate
            Content-Type: application/json
            Authorization: Bearer {token}
            
            {
              "prompt": "Create REST controller",
              "language": "java",
              "framework": "spring-boot"
            }
            ```
            
            ## Error Handling
            
            The API returns standard HTTP status codes and error responses in the following format:
            
            ```json
            {
              "timestamp": "2024-04-05T10:00:00Z",
              "status": 400,
              "error": "Bad Request",
              "message": "Validation failed",
              "path": "/api/chat"
            }
            ```
            
            ## Rate Limiting
            
            API requests are limited to 100 requests per minute per user.
            
            ---
            
            *API documentation generated using AuroraAI on """ + LocalDateTime.now().toLocalDate() + "*";
    }
    
    private String generateTechnicalDocs(DocumentationRequest request) {
        return """
            # Technical Documentation
            
            ## Architecture Overview
            
            This section describes the technical architecture and implementation details.
            
            ## System Architecture
            
            The system follows a microservices architecture with the following components:
            
            ### Backend Services
            
            - **Chat Service**: Handles AI chat interactions
            - **Knowledge Service**: Manages knowledge base
            - **Code Service**: Provides code generation and analysis
            - **Documentation Service**: Generates project documentation
            
            ### Database Schema
            
            #### Chat Messages
            ```sql
            CREATE TABLE chat_messages (
                id VARCHAR(36) PRIMARY KEY,
                user_id VARCHAR(100) NOT NULL,
                message TEXT NOT NULL,
                response TEXT NOT NULL,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            ```
            
            #### Knowledge Base
            ```sql
            CREATE TABLE knowledge_base (
                id VARCHAR(36) PRIMARY KEY,
                topic VARCHAR(200) UNIQUE NOT NULL,
                response TEXT NOT NULL,
                category VARCHAR(100) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            ```
            
            ## Technology Stack
            
            ### Backend
            - **Framework**: Spring Boot 3.2.5
            - **Language**: Java 17
            - **Database**: H2 (development), PostgreSQL (production)
            - **ORM**: Spring Data JPA
            
            ### Frontend
            - **Framework**: React 18
            - **Language**: TypeScript
            - **Styling**: Tailwind CSS
            - **Build Tool**: Vite
            
            ## Development Guidelines
            
            ### Code Standards
            - Follow Java naming conventions
            - Use meaningful variable names
            - Write comprehensive unit tests
            - Document public APIs
            
            ### Git Workflow
            - Feature branches for development
            - Pull requests for code review
            - Semantic versioning for releases
            
            ## Deployment
            
            ### Development Environment
            ```bash
            # Start backend
            mvn spring-boot:run
            
            # Start frontend
            npm run dev
            ```
            
            ### Production Deployment
            ```bash
            # Build application
            mvn clean package
            npm run build
            
            # Deploy using Docker
            docker-compose up -d
            ```
            
            ## Monitoring
            
            - Application logs: SLF4J with Logback
            - Performance metrics: Spring Boot Actuator
            - Error tracking: Global exception handler
            
            ---
            
            *Technical documentation generated using AuroraAI on """ + LocalDateTime.now().toLocalDate() + "*";
    }
    
    private String generateUserGuide(DocumentationRequest request) {
        return """
            # User Guide
            
            ## Getting Started
            
            Welcome to AuroraAI! This guide will help you get started with our AI-powered development assistant.
            
            ## Creating an Account
            
            1. Visit the signup page
            2. Enter your email and create a password
            3. Verify your email address
            4. Complete your profile
            
            ## Using the Chat Interface
            
            The chat interface allows you to interact with our AI assistant for development help.
            
            ### Starting a Conversation
            
            Simply type your question in the chat box and press Enter. The AI will respond with helpful information.
            
            ### Example Questions
            
            - "How do I implement pagination in Spring Data JPA?"
            - "Create a React component for user profiles"
            - "What's the best way to handle errors in Node.js?"
            
            ### Code Snippets
            
            When you ask for code examples, the AI will provide:
            - Complete code snippets
            - Explanations of the code
            - Related topics for further learning
            
            ## Managing Knowledge Base
            
            The knowledge base allows you to store and retrieve important information.
            
            ### Adding Knowledge
            
            1. Navigate to the Knowledge section
            2. Click "Add New Knowledge"
            3. Fill in the topic, response, and category
            4. Save the entry
            
            ### Searching Knowledge
            
            Use the search bar to find specific information in your knowledge base.
            
            ## Code Generation
            
            Our code generation feature helps you create code quickly.
            
            ### Generating Code
            
            1. Go to the Code Generator
            2. Enter a description of what you need
            3. Select the programming language and framework
            4. Click "Generate"
            
            ### Supported Languages
            
            - Java
            - JavaScript/TypeScript
            - Python
            - Go
            - Rust
            
            ## Code Analysis
            
            Get insights about your code quality and suggestions for improvement.
            
            ### Analyzing Code
            
            1. Paste your code in the analysis box
            2. Select the analysis type (security, performance, quality)
            3. Click "Analyze"
            4. Review the results and suggestions
            
            ## Documentation Generation
            
            Automatically generate professional documentation for your projects.
            
            ### Generating Documentation
            
            1. Provide your repository URL
            2. Select a documentation template
            3. Choose the output format
            4. Generate and download
            
            ## Tips and Best Practices
            
            ### Effective Prompts
            - Be specific about what you need
            - Provide context when possible
            - Ask follow-up questions for clarification
            
            ### Code Quality
            - Always review generated code
            - Test code before using in production
            - Follow your team's coding standards
            
            ### Knowledge Management
            - Regularly update your knowledge base
            - Use clear, descriptive topics
            - Organize content by categories
            
            ## Troubleshooting
            
            ### Common Issues
            
            **AI doesn't understand my question**
            - Try rephrasing your question
            - Provide more context
            - Break down complex questions
            
            **Generated code has errors**
            - Check for syntax errors
            - Verify imports and dependencies
            - Test in a development environment
            
            **Can't find knowledge entries**
            - Check spelling in search terms
            - Try different keywords
            - Browse by categories
            
            ## Getting Help
            
            If you need additional help:
            - Check our FAQ section
            - Contact support at support@aurorai.com
            - Join our community forum
            
            ---
            
            *User guide generated using AuroraAI on """ + LocalDateTime.now().toLocalDate() + "*";
    }
    
    private String generateChangelog(DocumentationRequest request) {
        return """
            # Changelog
            
            All notable changes to this project will be documented in this file.
            
            The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
            and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
            
            ## [Unreleased]
            
            ### Added
            - New chat features with conversation management
            - Enhanced code generation with multiple language support
            - Automatic documentation generation
            - Project analysis capabilities
            
            ### Changed
            - Improved error handling and validation
            - Enhanced UI/UX for better user experience
            - Optimized database queries for better performance
            
            ### Fixed
            - Fixed pagination issues in chat history
            - Resolved memory leak in code analysis
            - Fixed CORS configuration issues
            
            ## [1.2.0] - 2024-04-05
            
            ### Added
            - Code generation with AI assistance
            - Knowledge base management
            - Advanced search capabilities
            - Export functionality for documentation
            
            ### Changed
            - Redesigned chat interface
            - Improved response times
            - Enhanced security measures
            
            ### Fixed
            - Fixed authentication issues
            - Resolved database connection problems
            - Fixed UI rendering bugs
            
            ## [1.1.0] - 2024-03-15
            
            ### Added
            - Initial chat functionality
            - Basic code analysis
            - User authentication
            - Project management features
            
            ### Changed
            - Updated dependencies
            - Improved error messages
            - Enhanced logging
            
            ### Fixed
            - Fixed login issues
            - Resolved performance problems
            - Fixed data validation bugs
            
            ## [1.0.0] - 2024-02-01
            
            ### Added
            - Initial release
            - Basic UI framework
            - Core backend services
            - Database setup
            - User management
            
            ## [0.9.0] - 2024-01-15
            
            ### Added
            - Beta release
            - Core functionality
            - Basic chat features
            - Initial API endpoints
            
            ### Changed
            - Refactored architecture
            - Improved performance
            - Enhanced security
            
            ---
            
            *Changelog generated using AuroraAI on """ + LocalDateTime.now().toLocalDate() + "*";
    }
    
    private String generateStandardReadmePreview() {
        return """
            # Project Title
            
            ## Description
            
            Brief description of your project...
            
            ## Features
            
            - Feature 1
            - Feature 2
            - Feature 3
            
            ## Installation
            
            ```bash
            # Installation instructions
            ```
            
            ## Usage
            
            Usage instructions...
            
            ## License
            
            MIT License
            """;
    }
    
    private String generateApiDocsPreview() {
        return """
            # API Documentation
            
            ## Overview
            
            API overview and description...
            
            ## Endpoints
            
            ### GET /api/endpoint
            Description of the endpoint...
            
            ## Authentication
            
            Authentication details...
            """;
    }
    
    private String generateTechnicalDocsPreview() {
        return """
            # Technical Documentation
            
            ## Architecture
            
            System architecture overview...
            
            ## Implementation
            
            Implementation details...
            
            ## Deployment
            
            Deployment instructions...
            """;
    }
    
    private ApiDocumentation.ApiEndpoint createApiEndpoint(String method, String path, String summary, String description) {
        ApiDocumentation.ApiEndpoint endpoint = new ApiDocumentation.ApiEndpoint();
        endpoint.setMethod(method);
        endpoint.setPath(path);
        endpoint.setSummary(summary);
        endpoint.setDescription(description);
        
        Map<String, Object> responses = new HashMap<>();
        responses.put("200", Map.of("description", "Success"));
        responses.put("400", Map.of("description", "Bad Request"));
        responses.put("500", Map.of("description", "Internal Server Error"));
        endpoint.setResponses(responses);
        
        return endpoint;
    }
    
    private String generateHtmlExport(String content) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Documentation</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    h1 { color: #333; }
                    code { background: #f4f4f4; padding: 2px 4px; border-radius: 3px; }
                    pre { background: #f4f4f4; padding: 10px; border-radius: 5px; overflow-x: auto; }
                </style>
            </head>
            <body>
            """ + content.replace("\n", "<br>").replace("`", "<code>").replace("```", "</code><pre>").replace("```", "</pre>") + """
            </body>
            </html>
            """;
    }
    
    private byte[] generatePdfExport(String content) {
        // Mock PDF generation - in real implementation, use a PDF library
        return ("PDF Export\n\n" + content).getBytes();
    }
    
    private byte[] generateDocxExport(String content) {
        // Mock DOCX generation - in real implementation, use Apache POI
        return ("DOCX Export\n\n" + content).getBytes();
    }
}
