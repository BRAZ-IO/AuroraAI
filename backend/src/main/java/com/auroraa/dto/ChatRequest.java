package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatRequest {
    
    @NotBlank(message = "Message cannot be blank")
    @Size(max = 1000, message = "Message cannot exceed 1000 characters")
    private String message;
    
    @NotBlank(message = "User ID cannot be blank")
    @Size(max = 100, message = "User ID cannot exceed 100 characters")
    private String userId;
    
    @Size(max = 50, message = "Conversation ID cannot exceed 50 characters")
    private String conversationId;
    
    public ChatRequest() {}
    
    public ChatRequest(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
}
