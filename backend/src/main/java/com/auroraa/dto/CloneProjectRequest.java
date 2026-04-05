package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CloneProjectRequest {
    
    @NotBlank(message = "New project name cannot be blank")
    @Size(max = 100, message = "New project name cannot exceed 100 characters")
    private String newName;
    
    private String description;
    private String visibility;
    private boolean copyFiles;
    private boolean copyMetadata;
    private boolean copySettings;
    
    public CloneProjectRequest() {}
    
    public CloneProjectRequest(String newName) {
        this.newName = newName;
        this.copyFiles = true;
        this.copyMetadata = true;
        this.copySettings = true;
    }
    
    public String getNewName() { return newName; }
    public void setNewName(String newName) { this.newName = newName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    
    public boolean isCopyFiles() { return copyFiles; }
    public void setCopyFiles(boolean copyFiles) { this.copyFiles = copyFiles; }
    
    public boolean isCopyMetadata() { return copyMetadata; }
    public void setCopyMetadata(boolean copyMetadata) { this.copyMetadata = copyMetadata; }
    
    public boolean isCopySettings() { return copySettings; }
    public void setCopySettings(boolean copySettings) { this.copySettings = copySettings; }
}
