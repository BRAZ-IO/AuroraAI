package com.auroraa.service;

import com.auroraa.dto.ChatRequest;
import com.auroraa.dto.ChatResponse;
import com.auroraa.entity.ChatMessage;

import java.util.List;

public interface ChatServiceInterface {
    
    ChatResponse processMessage(ChatRequest request);
    
    List<ChatMessage> getChatHistory(String userId);
    
    List<ChatMessage> getRecentMessages(String userId, int limit);
    
    ChatMessage saveMessage(ChatMessage message);
    
    void deleteOldMessages(String userId, int daysToKeep);
    
    long getMessageCount(String userId);
}
