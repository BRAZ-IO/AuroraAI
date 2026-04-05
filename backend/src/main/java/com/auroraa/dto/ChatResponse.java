package com.auroraa.dto;

import java.time.LocalDateTime;

public class ChatResponse {
    
    private String response;
    private String messageId;
    private String conversationId;
    private LocalDateTime timestamp;
    
    public ChatResponse() {}
    
    public ChatResponse(String response, String messageId) {
        this.response = response;
        this.messageId = messageId;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
