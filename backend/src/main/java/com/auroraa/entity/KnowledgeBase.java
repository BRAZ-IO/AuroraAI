package com.auroraa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "knowledge_base")
public class KnowledgeBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    
    @Column(name = "topic", nullable = false, unique = true, length = 200)
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 200, message = "Topic must not exceed 200 characters")
    private String topic;
    
    @Column(name = "response", nullable = false, length = 2000, columnDefinition = "TEXT")
    @NotBlank(message = "Response cannot be blank")
    @Size(max = 2000, message = "Response must not exceed 2000 characters")
    private String response;
    
    @Column(name = "category", nullable = false, length = 100)
    @NotBlank(message = "Category cannot be blank")
    @Size(max = 100, message = "Category must not exceed 100 characters")
    private String category;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public KnowledgeBase() {
        this.id = UUID.randomUUID().toString();
    }
    
    public KnowledgeBase(String topic, String response, String category) {
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
