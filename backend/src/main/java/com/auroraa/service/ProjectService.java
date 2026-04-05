package com.auroraa.service;

import com.auroraa.dto.*;
import com.auroraa.entity.Project;
import com.auroraa.exception.BusinessException;
import com.auroraa.exception.ResourceNotFoundException;
import com.auroraa.exception.ValidationException;
import com.auroraa.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    
    @Autowired
    private ProjectRepository projectRepository;
    
    public ProjectResponse createProject(ProjectRequest request) {
        logger.info("Creating new project: {}", request.getName());
        
        validateProjectRequest(request);
        
        try {
            // Check for duplicate project name
            if (projectRepository.existsByOwnerIdAndName(request.getOwnerId(), request.getName())) {
                throw new BusinessException("Project with name '" + request.getName() + "' already exists");
            }
            
            Project project = new Project(request.getName(), request.getDescription(), request.getOwnerId());
            project.setCategory(request.getCategory());
            project.setVisibility(request.getVisibility() != null ? request.getVisibility() : "private");
            
            if (request.getSettings() != null) {
                project.setSettings(convertMapToJson(request.getSettings()));
            }
            
            if (request.getMetadata() != null) {
                project.setMetadata(convertMapToJson(request.getMetadata()));
            }
            
            Project saved = projectRepository.save(project);
            
            logger.info("Project created successfully: {}", saved.getId());
            return convertToResponse(saved);
            
        } catch (Exception e) {
            logger.error("Error creating project: {}", e.getMessage(), e);
            throw new ValidationException("Failed to create project: " + e.getMessage());
        }
    }
    
    public List<ProjectResponse> getAllProjects(int page, int size, String sortBy, String sortDir) {
        logger.debug("Fetching all projects - page: {}, size: {}, sort: {} {}", page, size, sortBy, sortDir);
        
        try {
            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            
            List<Project> projects = projectRepository.findAll(pageable).getContent();
            
            return projects.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error fetching projects: {}", e.getMessage(), e);
            throw new ValidationException("Failed to fetch projects: " + e.getMessage());
        }
    }
    
    public ProjectResponse getProjectById(String id) {
        logger.debug("Fetching project with ID: {}", id);
        
        try {
            Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
            
            logger.info("Retrieved project: {}", project.getName());
            return convertToResponse(project);
            
        } catch (Exception e) {
            logger.error("Error fetching project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    public ProjectResponse updateProject(String id, ProjectRequest request) {
        logger.info("Updating project with ID: {}", id);
        
        validateProjectRequest(request);
        
        try {
            Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
            
            // Check if user is the owner
            if (!existing.getOwnerId().equals(request.getOwnerId())) {
                throw new BusinessException("Only project owner can update the project");
            }
            
            // Check for name conflict
            if (!existing.getName().equals(request.getName()) && 
                projectRepository.existsByOwnerIdAndName(request.getOwnerId(), request.getName())) {
                throw new BusinessException("Project with name '" + request.getName() + "' already exists");
            }
            
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setCategory(request.getCategory());
            existing.setVisibility(request.getVisibility());
            
            if (request.getSettings() != null) {
                existing.setSettings(convertMapToJson(request.getSettings()));
            }
            
            if (request.getMetadata() != null) {
                existing.setMetadata(convertMapToJson(request.getMetadata()));
            }
            
            Project updated = projectRepository.save(existing);
            
            logger.info("Project updated successfully: {}", updated.getName());
            return convertToResponse(updated);
            
        } catch (Exception e) {
            logger.error("Error updating project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    public void deleteProject(String id) {
        logger.info("Deleting project with ID: {}", id);
        
        try {
            Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
            
            // Soft delete
            project.setActive(false);
            projectRepository.save(project);
            
            logger.info("Project deleted successfully: {}", id);
            
        } catch (Exception e) {
            logger.error("Error deleting project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    public List<ProjectFile> getProjectFiles(String projectId) {
        logger.debug("Fetching files for project: {}", projectId);
        
        try {
            // Mock implementation - in real scenario, would have ProjectFile entity
            List<ProjectFile> files = new ArrayList<>();
            
            // Generate mock files
            for (int i = 1; i <= 5; i++) {
                ProjectFile file = new ProjectFile(projectId, 
                    "file" + i + ".java", 
                    "/src/main/java/com/example/file" + i + ".java", 
                    "java");
                file.setContent("// Mock file content for " + file.getName());
                file.setLanguage("java");
                file.setSize(100 + i * 50);
                files.add(file);
            }
            
            logger.info("Retrieved {} files for project: {}", files.size(), projectId);
            return files;
            
        } catch (Exception e) {
            logger.error("Error fetching files for project {}: {}", projectId, e.getMessage(), e);
            throw new ValidationException("Failed to fetch project files: " + e.getMessage());
        }
    }
    
    public ProjectFile addProjectFile(String projectId, ProjectFileRequest request) {
        logger.info("Adding file to project {}: {}", projectId, request.getName());
        
        try {
            // Validate project exists
            if (!projectRepository.existsById(projectId)) {
                throw new ResourceNotFoundException("Project", "id", projectId);
            }
            
            ProjectFile file = new ProjectFile(projectId, request.getName(), request.getPath(), request.getType());
            file.setContent(request.getContent());
            file.setLanguage(request.getLanguage());
            file.setSize(request.getContent() != null ? request.getContent().length() : 0);
            
            // Update project file count and size
            projectRepository.incrementFileCount(projectId, 1);
            if (file.getSize() > 0) {
                projectRepository.incrementTotalSize(projectId, file.getSize());
            }
            
            logger.info("File added successfully: {}", file.getId());
            return file;
            
        } catch (Exception e) {
            logger.error("Error adding file to project {}: {}", projectId, e.getMessage(), e);
            throw new ValidationException("Failed to add project file: " + e.getMessage());
        }
    }
    
    public void deleteProjectFile(String projectId, String fileId) {
        logger.info("Deleting file {} from project: {}", fileId, projectId);
        
        try {
            // Mock implementation - would get file size first
            long fileSize = 100; // Mock size
            
            // Validate project exists
            if (!projectRepository.existsById(projectId)) {
                throw new ResourceNotFoundException("Project", "id", projectId);
            }
            
            // Update project file count and size
            projectRepository.decrementFileCount(projectId, 1);
            projectRepository.decrementTotalSize(projectId, fileSize);
            
            logger.info("File deleted successfully: {}", fileId);
            
        } catch (Exception e) {
            logger.error("Error deleting file {} from project {}: {}", fileId, projectId, e.getMessage(), e);
            throw e;
        }
    }
    
    public ProjectMetadata getProjectMetadata(String projectId) {
        logger.debug("Fetching metadata for project: {}", projectId);
        
        try {
            Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
            
            ProjectMetadata metadata = new ProjectMetadata(projectId);
            
            if (project.getMetadata() != null) {
                metadata.setCustomFields(convertJsonToMap(project.getMetadata()));
            }
            
            if (project.getSettings() != null) {
                metadata.setConfiguration(convertJsonToMap(project.getSettings()));
            }
            
            metadata.setLastUpdated(project.getUpdatedAt());
            
            logger.info("Retrieved metadata for project: {}", projectId);
            return metadata;
            
        } catch (Exception e) {
            logger.error("Error fetching metadata for project {}: {}", projectId, e.getMessage(), e);
            throw new ValidationException("Failed to fetch project metadata: " + e.getMessage());
        }
    }
    
    public ProjectMetadata updateProjectMetadata(String id, ProjectMetadata metadata) {
        logger.info("Updating metadata for project: {}", id);
        
        try {
            Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
            
            project.setMetadata(convertMapToJson(metadata.getCustomFields()));
            project.setSettings(convertMapToJson(metadata.getConfiguration()));
            project.setUpdatedAt(LocalDateTime.now());
            
            Project saved = projectRepository.save(project);
            
            metadata.setLastUpdated(saved.getUpdatedAt());
            metadata.setUpdatedBy("system");
            
            logger.info("Metadata updated successfully for project: {}", id);
            return metadata;
            
        } catch (Exception e) {
            logger.error("Error updating metadata for project {}: {}", id, e.getMessage(), e);
            throw new ValidationException("Failed to update project metadata: " + e.getMessage());
        }
    }
    
    public ProjectStats getProjectStats(String projectId) {
        logger.debug("Fetching stats for project: {}", projectId);
        
        try {
            Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
            
            ProjectStats stats = new ProjectStats(projectId);
            stats.setTotalFiles(project.getFileCount());
            stats.setTotalSize(project.getTotalSize());
            stats.setLastActivity(project.getUpdatedAt());
            
            // Mock additional stats
            Map<String, Integer> fileTypeBreakdown = new HashMap<>();
            fileTypeBreakdown.put("java", 3);
            fileTypeBreakdown.put("javascript", 2);
            stats.setFileTypeBreakdown(fileTypeBreakdown);
            
            Map<String, Integer> languageBreakdown = new HashMap<>();
            languageBreakdown.put("java", 3);
            languageBreakdown.put("javascript", 2);
            stats.setLanguageBreakdown(languageBreakdown);
            
            stats.setContributors(1);
            stats.setCommits(5);
            stats.setAverageFileSize(project.getTotalSize() > 0 ? (double) project.getTotalSize() / project.getFileCount() : 0);
            stats.setLargestFile("file1.java");
            stats.setMostRecentFile("file5.java");
            
            logger.info("Retrieved stats for project: {} - {} files, {} bytes", 
                       projectId, stats.getTotalFiles(), stats.getTotalSize());
            return stats;
            
        } catch (Exception e) {
            logger.error("Error fetching stats for project {}: {}", projectId, e.getMessage(), e);
            throw new ValidationException("Failed to fetch project stats: " + e.getMessage());
        }
    }
    
    public ProjectResponse cloneProject(String id, CloneProjectRequest request) {
        logger.info("Cloning project {} with name: {}", id, request.getNewName());
        
        try {
            Project original = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
            
            // Validate new name uniqueness
            if (projectRepository.existsByOwnerIdAndName(original.getOwnerId(), request.getNewName())) {
                throw new BusinessException("Project with name '" + request.getNewName() + "' already exists");
            }
            
            Project cloned = new Project(request.getNewName(), 
                request.getDescription() != null ? request.getDescription() : "Cloned from " + original.getName(),
                original.getOwnerId());
            
            cloned.setCategory(original.getCategory());
            cloned.setVisibility(request.getVisibility() != null ? request.getVisibility() : original.getVisibility());
            
            if (request.isCopyMetadata() && original.getMetadata() != null) {
                cloned.setMetadata(original.getMetadata());
            }
            
            if (request.isCopySettings() && original.getSettings() != null) {
                cloned.setSettings(original.getSettings());
            }
            
            cloned.setFileCount(request.isCopyFiles() ? original.getFileCount() : 0);
            cloned.setTotalSize(request.isCopyFiles() ? original.getTotalSize() : 0);
            
            Project saved = projectRepository.save(cloned);
            
            logger.info("Project cloned successfully: {} -> {}", id, saved.getId());
            return convertToResponse(saved);
            
        } catch (Exception e) {
            logger.error("Error cloning project {}: {}", id, e.getMessage(), e);
            throw new ValidationException("Failed to clone project: " + e.getMessage());
        }
    }
    
    public List<ProjectResponse> searchProjects(String query, String searchType, int limit) {
        logger.debug("Searching projects with query: '{}', type: {}, limit: {}", query, searchType, limit);
        
        try {
            List<Project> projects;
            
            switch (searchType.toLowerCase()) {
                case "name":
                    projects = projectRepository.searchProjects(query);
                    break;
                case "description":
                    projects = projectRepository.searchProjects(query);
                    break;
                default:
                    projects = projectRepository.searchProjects(query);
            }
            
            return projects.stream()
                .limit(limit)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error searching projects: {}", e.getMessage(), e);
            throw new ValidationException("Failed to search projects: " + e.getMessage());
        }
    }
    
    public List<String> getProjectCategories() {
        logger.debug("Fetching project categories");
        
        try {
            List<String> categories = projectRepository.findAllCategories();
            logger.info("Retrieved {} project categories", categories.size());
            return categories;
            
        } catch (Exception e) {
            logger.error("Error fetching project categories: {}", e.getMessage(), e);
            throw new ValidationException("Failed to fetch project categories: " + e.getMessage());
        }
    }
    
    public List<ProjectResponse> getProjectsByCategory(String category) {
        logger.debug("Fetching projects in category: {}", category);
        
        try {
            List<Project> projects = projectRepository.findByCategoryAndIsActiveOrderByCreatedAtDesc(category, true);
            
            return projects.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            logger.error("Error fetching projects in category {}: {}", category, e.getMessage(), e);
            throw new ValidationException("Failed to fetch projects by category: " + e.getMessage());
        }
    }
    
    public byte[] exportProject(String id, ExportRequest request) {
        logger.info("Exporting project {} in format: {}", id, request.getFormat());
        
        try {
            Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
            
            // Mock export implementation
            String content = generateExportContent(project, request);
            
            return switch (request.getFormat().toLowerCase()) {
                case "zip":
                    return generateZipExport(content, project, request);
                case "json":
                    return content.getBytes();
                case "csv":
                    return generateCsvExport(project, request);
                default:
                    return content.getBytes();
            };
            
        } catch (Exception e) {
            logger.error("Error exporting project {}: {}", id, e.getMessage(), e);
            throw new ValidationException("Failed to export project: " + e.getMessage());
        }
    }
    
    public List<ProjectActivity> getProjectActivity(String id, int limit) {
        logger.debug("Fetching activity for project: {}, limit: {}", id, limit);
        
        try {
            List<ProjectActivity> activity = new ArrayList<>();
            
            // Mock activity data
            for (int i = 1; i <= Math.min(limit, 5); i++) {
                ProjectActivity act = new ProjectActivity(id, "updated", "Project updated", "user" + i);
                act.setUserName("User " + i);
                act.setDetails(Map.of("changes", "Updated project settings"));
                activity.add(act);
            }
            
            logger.info("Retrieved {} activity records for project: {}", activity.size(), id);
            return activity;
            
        } catch (Exception e) {
            logger.error("Error fetching activity for project {}: {}", id, e.getMessage(), e);
            throw new ValidationException("Failed to fetch project activity: " + e.getMessage());
        }
    }
    
    private ProjectResponse convertToResponse(Project project) {
        ProjectResponse response = new ProjectResponse(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getOwnerId()
        );
        
        response.setCategory(project.getCategory());
        response.setVisibility(project.getVisibility());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        
        if (project.getSettings() != null) {
            response.setSettings(convertJsonToMap(project.getSettings()));
        }
        
        if (project.getMetadata() != null) {
            response.setMetadata(convertJsonToMap(project.getMetadata()));
        }
        
        if (project.getTags() != null) {
            response.setTags(Arrays.asList(project.getTags().split(",")));
        }
        
        response.setFileCount(project.getFileCount());
        response.setTotalSize(project.getTotalSize());
        
        return response;
    }
    
    private String convertMapToJson(Map<String, Object> map) {
        if (map == null || map.isEmpty()) return null;
        try {
            // Simple JSON conversion - in real implementation, use Jackson or Gson
            StringBuilder json = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (!first) json.append(",");
                json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
                first = false;
            }
            json.append("}");
            return json.toString();
        } catch (Exception e) {
            return null;
        }
    }
    
    private Map<String, Object> convertJsonToMap(String json) {
        if (json == null || json.trim().isEmpty()) return new HashMap<>();
        try {
            // Simple JSON parsing - in real implementation, use Jackson or Gson
            Map<String, Object> map = new HashMap<>();
            // This is a simplified implementation
            return map;
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
    
    private void validateProjectRequest(ProjectRequest request) {
        if (request == null) {
            throw new ValidationException("Project request cannot be null");
        }
        if (request.getOwnerId() == null || request.getOwnerId().trim().isEmpty()) {
            throw new ValidationException("Owner ID cannot be null or empty");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ValidationException("Project name cannot be null or empty");
        }
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new ValidationException("Project description cannot be null or empty");
        }
    }
    
    private String generateExportContent(Project project, ExportRequest request) {
        StringBuilder content = new StringBuilder();
        
        content.append("# ").append(project.getName()).append("\n\n");
        content.append("## Description\n").append(project.getDescription()).append("\n\n");
        content.append("## Category\n").append(project.getCategory()).append("\n\n");
        content.append("## Visibility\n").append(project.getVisibility()).append("\n\n");
        content.append("## Created\n").append(project.getCreatedAt()).append("\n\n");
        content.append("## Updated\n").append(project.getUpdatedAt()).append("\n\n");
        content.append("## Files\n").append(project.getFileCount()).append(" files\n\n");
        content.append("## Total Size\n").append(project.getTotalSize()).append(" bytes\n\n");
        
        if (request.isIncludeMetadata() && project.getMetadata() != null) {
            content.append("## Metadata\n").append(project.getMetadata()).append("\n\n");
        }
        
        if (request.isIncludeSettings() && project.getSettings() != null) {
            content.append("## Settings\n").append(project.getSettings()).append("\n\n");
        }
        
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            content.append("## Tags\n").append(String.join(", ", request.getTags())).append("\n\n");
        }
        
        return content.toString();
    }
    
    private byte[] generateZipExport(String content, Project project, ExportRequest request) {
        // Mock ZIP generation - in real implementation, use ZipOutputStream
        String zipContent = "ZIP export for project: " + project.getName() + "\n\n" + content;
        return zipContent.getBytes();
    }
    
    private byte[] generateCsvExport(Project project, ExportRequest request) {
        StringBuilder csv = new StringBuilder();
        csv.append("Name,Description,Category,Visibility,Created,Updated,Files,Size\n");
        csv.append("\"").append(project.getName()).append("\",");
        csv.append("\"").append(project.getDescription()).append("\",");
        csv.append(project.getCategory()).append("\",");
        csv.append(project.getVisibility()).append("\",");
        csv.append(project.getCreatedAt()).append("\",");
        csv.append(project.getUpdatedAt()).append("\",");
        csv.append(project.getFileCount()).append("\",");
        csv.append(project.getTotalSize()).append("\n");
        
        return csv.toString().getBytes();
    }
}
