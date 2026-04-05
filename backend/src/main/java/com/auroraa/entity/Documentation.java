package com.auroraa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documentations")
public class Documentation {
    
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
    
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Content cannot be blank")
    private String content;
    
    @Column(name = "documentation_type", nullable = false, length = 50)
    @NotBlank(message = "Documentation type cannot be blank")
    @Size(max = 50, message = "Documentation type cannot exceed 50 characters")
    private String documentationType; // readme, api, user_guide, technical
    
    @Column(name = "format", nullable = false, length = 20)
    @NotBlank(message = "Format cannot be blank")
    @Size(max = 20, message = "Format cannot exceed 20 characters")
    private String format; // markdown, html, pdf
    
    @Column(name = "language", length = 50)
    private String language;
    
    @Column(name = "version", length = 50)
    private String version;
    
    @Column(name = "tags", length = 500)
    private String tags;
    
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    
    @Column(name = "owner_id", nullable = false, length = 100)
    @NotBlank(message = "Owner ID cannot be blank")
    private String ownerId;
    
    @Column(name = "project_id", length = 100)
    private String projectId;
    
    @Column(name = "word_count")
    private Integer wordCount;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    public Documentation() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isPublic = false;
        this.format = "markdown";
        this.isActive = true;
    }
    
    public Documentation(String title, String content, String documentationType, String ownerId) {
        this();
        this.title = title;
        this.content = content;
        this.documentationType = documentationType;
        this.ownerId = ownerId;
    }
    
    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
        if (this.wordCount == null && this.content != null) {
            this.wordCount = this.content.split("\\s+").length;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        if (this.content != null) {
            this.wordCount = this.content.split("\\s+").length;
        }
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
    
    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDocumentationType() { return documentationType; }
    public void setDocumentationType(String documentationType) { 
        this.documentationType = documentationType;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { 
        this.format = format;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { 
        this.language = language;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { 
        this.version = version;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getTags() { return tags; }
    public void setTags(String tags) { 
        this.tags = tags;
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
    
    public Integer getWordCount() { return wordCount; }
    public void setWordCount(Integer wordCount) { 
        this.wordCount = wordCount;
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
}
