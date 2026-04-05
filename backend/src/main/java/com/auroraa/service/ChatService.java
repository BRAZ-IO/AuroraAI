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
        
        ChatMessage chatMessage = new ChatMessage(
            request.getUserId(),
            request.getMessage(),
            response
        );
        
        chatMessageRepository.save(chatMessage);
        
        return new ChatResponse(
            UUID.randomUUID().toString(),
            response,
            LocalDateTime.now()
        );
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
        } else {
            return "Entendi sua mensagem. Estou processando e respondendo...";
        }
    }
}
