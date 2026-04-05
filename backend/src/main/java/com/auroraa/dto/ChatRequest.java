package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {
    
    @NotBlank(message = "Message cannot be blank")
    private String message;
    
    @NotBlank(message = "User ID cannot be blank")
    private String userId;
    
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
