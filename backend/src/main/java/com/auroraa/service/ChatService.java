package com.auroraa.service;

import com.auroraa.dto.ChatRequest;
import com.auroraa.dto.ChatResponse;
import com.auroraa.entity.ChatMessage;
import com.auroraa.exception.ValidationException;
import com.auroraa.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService implements ChatServiceInterface {
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    @Override
    public ChatResponse processMessage(ChatRequest request) {
        validateChatRequest(request);
        
        // TODO: Integrar com IA quando disponível
        String response = generateMockResponse(request.getMessage());
        String codeSnippet = generateCodeSnippet(request.getMessage());
        List<String> relatedTopics = generateRelatedTopics(request.getMessage());
        
        ChatMessage chatMessage = new ChatMessage(
            request.getUserId(),
            request.getMessage(),
            response
        );
        
        chatMessageRepository.save(chatMessage);
        
        ChatResponse chatResponse = new ChatResponse(
            UUID.randomUUID().toString(),
            response,
            LocalDateTime.now()
        );
        
        chatResponse.setConversationId(request.getConversationId());
        chatResponse.setCodeSnippet(codeSnippet);
        chatResponse.setRelatedTopics(relatedTopics);
        
        return chatResponse;
    }
    
    @Override
    public List<ChatMessage> getChatHistory(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        return chatMessageRepository.findByUserIdOrderByTimestampDesc(userId);
    }
    
    @Override
    public List<ChatMessage> getRecentMessages(String userId, int limit) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        if (limit <= 0 || limit > 100) {
            throw new ValidationException("Limit must be between 1 and 100");
        }
        
        List<ChatMessage> messages = chatMessageRepository.findRecentMessagesByUser(userId);
        return messages.stream().limit(limit).toList();
    }
    
    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        if (message == null) {
            throw new ValidationException("Chat message cannot be null");
        }
        if (message.getUserId() == null || message.getUserId().trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
            throw new ValidationException("Message cannot be null or empty");
        }
        
        return chatMessageRepository.save(message);
    }
    
    @Override
    public void deleteOldMessages(String userId, int daysToKeep) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        if (daysToKeep < 0) {
            throw new ValidationException("Days to keep must be non-negative");
        }
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        chatMessageRepository.deleteByUserIdAndTimestampBefore(userId, cutoffDate);
    }
    
    @Override
    public long getMessageCount(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        return chatMessageRepository.countMessagesByUserSince(userId, since);
    }
    
    private void validateChatRequest(ChatRequest request) {
        if (request == null) {
            throw new ValidationException("Chat request cannot be null");
        }
        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or empty");
        }
        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            throw new ValidationException("Message cannot be null or empty");
        }
        if (request.getMessage().length() > 1000) {
            throw new ValidationException("Message cannot exceed 1000 characters");
        }
    }
    
    private String generateMockResponse(String message) {
        if (message.toLowerCase().contains("olá") || message.toLowerCase().contains("oi")) {
            return "Olá! Como posso ajudar você hoje?";
        } else if (message.toLowerCase().contains("ajuda")) {
            return "Estou aqui para ajudar! O que você precisa?";
        } else if (message.toLowerCase().contains("spring") || message.toLowerCase().contains("java")) {
            return "Posso ajudar com Spring Boot e Java! Qual sua dúvida específica?";
        } else if (message.toLowerCase().contains("react") || message.toLowerCase().contains("frontend")) {
            return "React é excelente para frontend! Precisa de ajuda com componentes ou hooks?";
        } else {
            return "Entendi sua mensagem. Estou processando e respondendo...";
        }
    }
    
    private String generateCodeSnippet(String message) {
        String lowerMessage = message.toLowerCase();
        
        if (lowerMessage.contains("pagination") || lowerMessage.contains("paginar")) {
            return "Page<User> findAll(Pageable pageable);";
        } else if (lowerMessage.contains("controller") || lowerMessage.contains("rest")) {
            return "@RestController\n@RequestMapping('/api/users')\npublic class UserController {\n    // implementation\n}";
        } else if (lowerMessage.contains("react") || lowerMessage.contains("component")) {
            return "const MyComponent = () => {\n  return <div>Hello World</div>;\n};\nexport default MyComponent;";
        } else if (lowerMessage.contains("database") || lowerMessage.contains("jpa")) {
            return "@Entity\n@Table(name = \"users\")\npublic class User {\n    @Id\n    private String id;\n    // fields\n}";
        }
        
        return null; // No code snippet applicable
    }
    
    private List<String> generateRelatedTopics(String message) {
        String lowerMessage = message.toLowerCase();
        
        if (lowerMessage.contains("pagination")) {
            return List.of("Sorting", "Filtering", "Performance", "Database Optimization");
        } else if (lowerMessage.contains("spring")) {
            return List.of("Spring Boot", "Spring Data JPA", "Spring Security", "Microservices");
        } else if (lowerMessage.contains("react")) {
            return List.of("Components", "Hooks", "State Management", "JavaScript ES6+");
        } else if (lowerMessage.contains("database")) {
            return List.of("SQL", "JPA", "Hibernate", "Database Design");
        } else if (lowerMessage.contains("api")) {
            return List.of("REST", "HTTP Methods", "Authentication", "API Design");
        }
        
        return List.of("Programming", "Development", "Best Practices", "Documentation");
    }
}
