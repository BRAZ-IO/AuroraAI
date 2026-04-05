package com.auroraa.controller;

import com.auroraa.dto.*;
import com.auroraa.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    
    @Autowired
    private ProjectService projectService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(@RequestBody ProjectRequest request) {
        logger.info("Creating new project: {}", request.getName());
        
        try {
            ProjectResponse response = projectService.createProject(request);
            logger.info("Project created successfully with ID: {}", response.getId());
            return response;
        } catch (Exception e) {
            logger.error("Error creating project: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        logger.debug("Fetching all projects - page: {}, size: {}, sort: {} {}", page, size, sortBy, sortDir);
        
        try {
            List<ProjectResponse> projects = projectService.getAllProjects(page, size, sortBy, sortDir);
            logger.info("Retrieved {} projects", projects.size());
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            logger.error("Error fetching projects: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable String id) {
        logger.debug("Fetching project with ID: {}", id);
        
        try {
            ProjectResponse project = projectService.getProjectById(id);
            logger.info("Retrieved project: {}", project.getName());
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            logger.error("Error fetching project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable String id, 
            @RequestBody ProjectRequest request) {
        logger.info("Updating project with ID: {}", id);
        
        try {
            ProjectResponse response = projectService.updateProject(id, request);
            logger.info("Project updated successfully: {}", response.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable String id) {
        logger.info("Deleting project with ID: {}", id);
        
        try {
            projectService.deleteProject(id);
            logger.info("Project deleted successfully: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/{id}/files")
    public ResponseEntity<List<ProjectFile>> getProjectFiles(@PathVariable String id) {
        logger.debug("Fetching files for project: {}", id);
        
        try {
            List<ProjectFile> files = projectService.getProjectFiles(id);
            logger.info("Retrieved {} files for project: {}", files.size(), id);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            logger.error("Error fetching files for project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/{id}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectFile addProjectFile(
            @PathVariable String id,
            @RequestBody ProjectFileRequest request) {
        logger.info("Adding file to project {}: {}", id, request.getName());
        
        try {
            ProjectFile file = projectService.addProjectFile(id, request);
            logger.info("File added successfully: {}", file.getId());
            return file;
        } catch (Exception e) {
            logger.error("Error adding file to project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @DeleteMapping("/{id}/files/{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectFile(@PathVariable String id, @PathVariable String fileId) {
        logger.info("Deleting file {} from project: {}", fileId, id);
        
        try {
            projectService.deleteProjectFile(id, fileId);
            logger.info("File deleted successfully: {}", fileId);
        } catch (Exception e) {
            logger.error("Error deleting file {} from project {}: {}", fileId, id, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/{id}/metadata")
    public ResponseEntity<ProjectMetadata> getProjectMetadata(@PathVariable String id) {
        logger.debug("Fetching metadata for project: {}", id);
        
        try {
            ProjectMetadata metadata = projectService.getProjectMetadata(id);
            logger.info("Retrieved metadata for project: {}", id);
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            logger.error("Error fetching metadata for project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @PutMapping("/{id}/metadata")
    public ResponseEntity<ProjectMetadata> updateProjectMetadata(
            @PathVariable String id,
            @RequestBody ProjectMetadata metadata) {
        logger.info("Updating metadata for project: {}", id);
        
        try {
            ProjectMetadata updated = projectService.updateProjectMetadata(id, metadata);
            logger.info("Metadata updated successfully for project: {}", id);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Error updating metadata for project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/{id}/stats")
    public ResponseEntity<ProjectStats> getProjectStats(@PathVariable String id) {
        logger.debug("Fetching stats for project: {}", id);
        
        try {
            ProjectStats stats = projectService.getProjectStats(id);
            logger.info("Retrieved stats for project: {} - {} files, {} bytes", 
                       id, stats.getTotalFiles(), stats.getTotalSize());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("Error fetching stats for project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/{id}/clone")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse cloneProject(@PathVariable String id, @RequestBody CloneProjectRequest request) {
        logger.info("Cloning project {} with name: {}", id, request.getNewName());
        
        try {
            ProjectResponse cloned = projectService.cloneProject(id, request);
            logger.info("Project cloned successfully: {} -> {}", id, cloned.getId());
            return cloned;
        } catch (Exception e) {
            logger.error("Error cloning project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ProjectResponse>> searchProjects(
            @RequestParam String query,
            @RequestParam(defaultValue = "name") String searchType,
            @RequestParam(defaultValue = "10") int limit) {
        logger.debug("Searching projects with query: '{}', type: {}, limit: {}", query, searchType, limit);
        
        try {
            List<ProjectResponse> results = projectService.searchProjects(query, searchType, limit);
            logger.info("Found {} projects matching query", results.size());
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            logger.error("Error searching projects: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getProjectCategories() {
        logger.debug("Fetching project categories");
        
        try {
            List<String> categories = projectService.getProjectCategories();
            logger.info("Retrieved {} project categories", categories.size());
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching project categories: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByCategory(@PathVariable String category) {
        logger.debug("Fetching projects in category: {}", category);
        
        try {
            List<ProjectResponse> projects = projectService.getProjectsByCategory(category);
            logger.info("Retrieved {} projects in category: {}", projects.size(), category);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            logger.error("Error fetching projects in category {}: {}", category, e.getMessage(), e);
            throw e;
        }
    }
    
    @PostMapping("/{id}/export")
    public ResponseEntity<byte[]> exportProject(
            @PathVariable String id,
            @RequestBody ExportRequest request) {
        logger.info("Exporting project {} in format: {}", id, request.getFormat());
        
        try {
            byte[] exportedData = projectService.exportProject(id, request);
            
            String contentType = switch (request.getFormat().toLowerCase()) {
                case "zip" -> "application/zip";
                case "json" -> "application/json";
                case "csv" -> "text/csv";
                default -> "application/octet-stream";
            };
            
            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"project-" + id + "." + request.getFormat() + "\"")
                .header("Content-Type", contentType)
                .body(exportedData);
                
        } catch (Exception e) {
            logger.error("Error exporting project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/{id}/activity")
    public ResponseEntity<List<ProjectActivity>> getProjectActivity(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") int limit) {
        logger.debug("Fetching activity for project: {}, limit: {}", id, limit);
        
        try {
            List<ProjectActivity> activity = projectService.getProjectActivity(id, limit);
            logger.info("Retrieved {} activity records for project: {}", activity.size(), id);
            return ResponseEntity.ok(activity);
        } catch (Exception e) {
            logger.error("Error fetching activity for project {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
