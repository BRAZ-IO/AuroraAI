package com.auroraa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_history")
public class ChatHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    
    @Column(name = "conversation_id", length = 100)
    private String conversationId;
    
    @Column(name = "user_id", nullable = false, length = 100)
    @NotBlank(message = "User ID cannot be blank")
    private String userId;
    
    @Column(name = "message", nullable = false, length = 1000, columnDefinition = "TEXT")
    @NotBlank(message = "Message cannot be blank")
    @Size(max = 1000, message = "Message must not exceed 1000 characters")
    private String message;
    
    @Column(name = "response", nullable = false, length = 1000, columnDefinition = "TEXT")
    @NotBlank(message = "Response cannot be blank")
    @Size(max = 1000, message = "Response must not exceed 1000 characters")
    private String response;
    
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    public ChatHistory() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.isActive = true;
    }
    
    public ChatHistory(String userId, String message, String response) {
        this();
        this.userId = userId;
        this.message = message;
        this.response = response;
    }
    
    public ChatHistory(String conversationId, String userId, String message, String response) {
        this(userId, message, response);
        this.conversationId = conversationId;
    }
    
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
