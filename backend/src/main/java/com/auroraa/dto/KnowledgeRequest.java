package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;

public class KnowledgeRequest {
    
    @NotBlank(message = "Topic cannot be blank")
    private String topic;
    
    @NotBlank(message = "Response cannot be blank")
    private String response;
    
    private String category;
    
    public KnowledgeRequest() {}
    
    public KnowledgeRequest(String topic, String response, String category) {
        this.topic = topic;
        this.response = response;
        this.category = category;
    }
    
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
