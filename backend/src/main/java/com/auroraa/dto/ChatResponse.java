package com.auroraa.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ChatResponse {
    
    private String response;
    private String messageId;
    private String conversationId;
    private LocalDateTime timestamp;
    private String codeSnippet;
    private List<String> relatedTopics;
    
    public ChatResponse() {}
    
    public ChatResponse(String response, String messageId) {
        this.response = response;
        this.messageId = messageId;
        this.timestamp = LocalDateTime.now();
    }
    
    public ChatResponse(String messageId, String response, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.response = response;
        this.timestamp = timestamp;
    }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getCodeSnippet() { return codeSnippet; }
    public void setCodeSnippet(String codeSnippet) { this.codeSnippet = codeSnippet; }
    
    public List<String> getRelatedTopics() { return relatedTopics; }
    public void setRelatedTopics(List<String> relatedTopics) { this.relatedTopics = relatedTopics; }
}
