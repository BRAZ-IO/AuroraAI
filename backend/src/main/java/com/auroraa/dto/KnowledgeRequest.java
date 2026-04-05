package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class KnowledgeRequest {
    
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 200, message = "Topic cannot exceed 200 characters")
    private String topic;
    
    @NotBlank(message = "Response cannot be blank")
    @Size(max = 2000, message = "Response cannot exceed 2000 characters")
    private String response;
    
    @NotBlank(message = "Category cannot be blank")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
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
