package com.auroraa.dto;

import java.time.LocalDateTime;

public class KnowledgeResponse {
    
    private String id;
    private String topic;
    private String response;
    private String category;
    private LocalDateTime createdAt;
    
    public KnowledgeResponse() {}
    
    public KnowledgeResponse(String id, String topic, String response, String category) {
        this.id = id;
        this.topic = topic;
        this.response = response;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
