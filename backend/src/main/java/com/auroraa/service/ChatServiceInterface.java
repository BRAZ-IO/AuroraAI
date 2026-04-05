package com.auroraa.service;

import com.auroraa.dto.ChatRequest;
import com.auroraa.dto.ChatResponse;
import com.auroraa.entity.ChatHistory;

import java.util.List;

public interface ChatServiceInterface {
    
    ChatResponse processMessage(ChatRequest request);
    
    List<ChatHistory> getChatHistory(String userId);
    
    List<ChatHistory> getRecentMessages(String userId, int limit);
    
    ChatHistory saveMessage(ChatHistory message);
    
    void deleteOldMessages(String userId, int daysToKeep);
    
    long getMessageCount(String userId);
}
