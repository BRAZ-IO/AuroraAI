package com.auroraa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "code_snippets")
public class CodeSnippet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    
    @Column(name = "title", nullable = false, length = 200)
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;
    
    @Column(name = "description", length = 1000, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "code", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Code cannot be blank")
    private String code;
    
    @Column(name = "language", nullable = false, length = 50)
    @NotBlank(message = "Language cannot be blank")
    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;
    
    @Column(name = "tags", length = 500)
    private String tags;
    
    @Column(name = "category", length = 100)
    private String category;
    
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    
    @Column(name = "owner_id", nullable = false, length = 100)
    @NotBlank(message = "Owner ID cannot be blank")
    private String ownerId;
    
    @Column(name = "project_id", length = 100)
    private String projectId;
    
    @Column(name = "usage_count", nullable = false)
    private int usageCount;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    public CodeSnippet() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isPublic = false;
        this.usageCount = 0;
        this.isActive = true;
    }
    
    public CodeSnippet(String title, String code, String language, String ownerId) {
        this();
        this.title = title;
        this.code = code;
        this.language = language;
        this.ownerId = ownerId;
    }
    
    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { 
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCode() { return code; }
    public void setCode(String code) { 
        this.code = code;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { 
        this.language = language;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getTags() { return tags; }
    public void setTags(String tags) { 
        this.tags = tags;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { 
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean isPublic) { 
        this.isPublic = isPublic;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { 
        this.projectId = projectId;
        this.updatedAt = LocalDateTime.now();
    }
    
    public int getUsageCount() { return usageCount; }
    public void setUsageCount(int usageCount) { 
        this.usageCount = usageCount;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { 
        isActive = active;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void incrementUsage() {
        this.usageCount++;
        this.updatedAt = LocalDateTime.now();
    }
}
