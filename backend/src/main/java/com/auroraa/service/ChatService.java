package com.auroraa.service;

import com.auroraa.dto.ChatRequest;
import com.auroraa.dto.ChatResponse;
import com.auroraa.entity.ChatMessage;
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
        return chatMessageRepository.findByUserIdOrderByTimestampDesc(userId);
    }
    
    @Override
    public List<ChatMessage> getRecentMessages(String userId, int limit) {
        List<ChatMessage> messages = chatMessageRepository.findRecentMessagesByUser(userId);
        return messages.stream().limit(limit).toList();
    }
    
    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }
    
    @Override
    public void deleteOldMessages(String userId, int daysToKeep) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        chatMessageRepository.deleteByUserIdAndTimestampBefore(userId, cutoffDate);
    }
    
    @Override
    public long getMessageCount(String userId) {
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        return chatMessageRepository.countMessagesByUserSince(userId, since);
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
