package com.auroraa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Project name cannot be blank")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    private String name;
    
    @Column(name = "description", nullable = false, length = 500, columnDefinition = "TEXT")
    @NotBlank(message = "Description cannot be blank")
    private String description;
    
    @Column(name = "category", length = 50)
    private String category;
    
    @Column(name = "visibility", nullable = false)
    private String visibility; // public, private, team
    
    @Column(name = "owner_id", nullable = false, length = 100)
    @NotBlank(message = "Owner ID cannot be blank")
    private String ownerId;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "settings", columnDefinition = "TEXT")
    private String settings;
    
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
    
    @Column(name = "tags", length = 500)
    private String tags;
    
    @Column(name = "file_count", nullable = false)
    private int fileCount;
    
    @Column(name = "total_size", nullable = false)
    private long totalSize;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    public Project() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.visibility = "private";
        this.fileCount = 0;
        this.totalSize = 0;
        this.isActive = true;
    }
    
    public Project(String name, String description, String ownerId) {
        this();
        this.name = name;
        this.description = description;
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
    
    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { 
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { 
        this.visibility = visibility;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public String getSettings() { return settings; }
    public void setSettings(String settings) { 
        this.settings = settings;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { 
        this.metadata = metadata;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getTags() { return tags; }
    public void setTags(String tags) { 
        this.tags = tags;
        this.updatedAt = LocalDateTime.now();
    }
    
    public int getFileCount() { return fileCount; }
    public void setFileCount(int fileCount) { 
        this.fileCount = fileCount;
        this.updatedAt = LocalDateTime.now();
    }
    
    public long getTotalSize() { return totalSize; }
    public void setTotalSize(long totalSize) { 
        this.totalSize = totalSize;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { 
        isActive = active;
        this.updatedAt = LocalDateTime.now();
    }
}
