package com.auroraa.repository;

import com.auroraa.entity.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentationRepository extends JpaRepository<Documentation, String> {
    
    List<Documentation> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
    
    List<Documentation> findByOwnerIdAndIsActiveOrderByCreatedAtDesc(String ownerId, boolean isActive);
    
    List<Documentation> findByIsPublicAndIsActiveOrderByCreatedAtDesc(boolean isPublic, boolean isActive);
    
    List<Documentation> findByProjectIdAndIsActiveOrderByCreatedAtDesc(String projectId, boolean isActive);
    
    List<Documentation> findByDocumentationTypeAndIsActiveOrderByCreatedAtDesc(String documentationType, boolean isActive);
    
    List<Documentation> findByLanguageAndIsActiveOrderByCreatedAtDesc(String language, boolean isActive);
    
    List<Documentation> findByFormatAndIsActiveOrderByCreatedAtDesc(String format, boolean isActive);
    
    Optional<Documentation> findByIdAndOwnerId(String id, String ownerId);
    
    @Query("SELECT d FROM Documentation d WHERE d.isActive = true AND (LOWER(d.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Documentation> searchDocumentations(@Param("query") String query);
    
    @Query("SELECT d FROM Documentation d WHERE d.ownerId = :ownerId AND d.isActive = true AND (LOWER(d.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Documentation> searchDocumentationsByOwner(@Param("ownerId") String ownerId, @Param("query") String query);
    
    @Query("SELECT DISTINCT d.documentationType FROM Documentation d WHERE d.isActive = true")
    List<String> findAllDocumentationTypes();
    
    @Query("SELECT DISTINCT d.language FROM Documentation d WHERE d.isActive = true")
    List<String> findAllLanguages();
    
    @Query("SELECT d FROM Documentation d WHERE d.ownerId = :ownerId AND d.documentationType = :type AND d.isActive = true")
    List<Documentation> findByOwnerAndDocumentationType(@Param("ownerId") String ownerId, @Param("type") String type);
    
    @Query("SELECT d FROM Documentation d WHERE d.isPublic = true AND d.documentationType = :type AND d.isActive = true")
    List<Documentation> findPublicDocumentationsByType(@Param("type") String type);
    
    @Query("SELECT d FROM Documentation d WHERE d.ownerId = :ownerId AND d.tags LIKE CONCAT('%', :tag, '%') AND d.isActive = true")
    List<Documentation> findByOwnerAndTag(@Param("ownerId") String ownerId, @Param("tag") String tag);
    
    @Query("SELECT d FROM Documentation d WHERE d.ownerId = :ownerId AND d.updatedAt >= :since ORDER BY d.updatedAt DESC")
    List<Documentation> findRecentlyUpdatedDocumentationsByOwner(@Param("ownerId") String ownerId, @Param("since") LocalDateTime since);
    
    @Query("SELECT d FROM Documentation d WHERE d.projectId = :projectId AND d.isActive = true")
    List<Documentation> findByProject(@Param("projectId") String projectId);
    
    @Query("SELECT d FROM Documentation d WHERE d.ownerId = :ownerId AND d.version = :version AND d.isActive = true")
    List<Documentation> findByOwnerAndVersion(@Param("ownerId") String ownerId, @Param("version") String version);
    
    @Query("SELECT COUNT(d) FROM Documentation d WHERE d.ownerId = :ownerId AND d.isActive = true")
    long countActiveDocumentationsByOwner(@Param("ownerId") String ownerId);
    
    @Query("SELECT COUNT(d) FROM Documentation d WHERE d.documentationType = :type AND d.isActive = true")
    long countDocumentationsByType(@Param("type") String type);
    
    @Query("SELECT SUM(d.wordCount) FROM Documentation d WHERE d.ownerId = :ownerId AND d.isActive = true")
    Long totalWordCountByOwner(@Param("ownerId") String ownerId);
    
    boolean existsByOwnerIdAndTitle(String ownerId, String title);
    
    void deleteByOwnerIdAndId(String ownerId, String id);
}
