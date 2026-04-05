package com.auroraa.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

public class ExportRequest {
    
    @NotBlank(message = "Format cannot be blank")
    private String format;
    
    private boolean includeFiles;
    private boolean includeMetadata;
    private boolean includeSettings;
    private Map<String, Object> options;
    
    public ExportRequest() {
        this.format = "zip";
        this.includeFiles = true;
        this.includeMetadata = true;
        this.includeSettings = false;
    }
    
    public ExportRequest(String format) {
        this.format = format;
        this.includeFiles = true;
        this.includeMetadata = true;
        this.includeSettings = false;
    }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    
    public boolean isIncludeFiles() { return includeFiles; }
    public void setIncludeFiles(boolean includeFiles) { this.includeFiles = includeFiles; }
    
    public boolean isIncludeMetadata() { return includeMetadata; }
    public void setIncludeMetadata(boolean includeMetadata) { this.includeMetadata = includeMetadata; }
    
    public boolean isIncludeSettings() { return includeSettings; }
    public void setIncludeSettings(boolean includeSettings) { this.includeSettings = includeSettings; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
}
