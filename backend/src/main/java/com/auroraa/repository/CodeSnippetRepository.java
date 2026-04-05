package com.auroraa.repository;

import com.auroraa.entity.CodeSnippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, String> {
    
    List<CodeSnippet> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
    
    List<CodeSnippet> findByOwnerIdAndIsActiveOrderByCreatedAtDesc(String ownerId, boolean isActive);
    
    List<CodeSnippet> findByIsPublicAndIsActiveOrderByCreatedAtDesc(boolean isPublic, boolean isActive);
    
    List<CodeSnippet> findByProjectIdAndIsActiveOrderByCreatedAtDesc(String projectId, boolean isActive);
    
    List<CodeSnippet> findByLanguageAndIsActiveOrderByCreatedAtDesc(String language, boolean isActive);
    
    List<CodeSnippet> findByCategoryAndIsActiveOrderByCreatedAtDesc(String category, boolean isActive);
    
    Optional<CodeSnippet> findByIdAndOwnerId(String id, String ownerId);
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.isActive = true AND (LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.code) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<CodeSnippet> searchSnippets(@Param("query") String query);
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.ownerId = :ownerId AND s.isActive = true AND (LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<CodeSnippet> searchSnippetsByOwner(@Param("ownerId") String ownerId, @Param("query") String query);
    
    @Query("SELECT DISTINCT s.language FROM CodeSnippet s WHERE s.isActive = true")
    List<String> findAllLanguages();
    
    @Query("SELECT DISTINCT s.category FROM CodeSnippet s WHERE s.isActive = true")
    List<String> findAllCategories();
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.ownerId = :ownerId AND s.language = :language AND s.isActive = true")
    List<CodeSnippet> findByOwnerAndLanguage(@Param("ownerId") String ownerId, @Param("language") String language);
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.isPublic = true AND s.language = :language AND s.isActive = true")
    List<CodeSnippet> findPublicSnippetsByLanguage(@Param("language") String language);
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.ownerId = :ownerId AND s.tags LIKE CONCAT('%', :tag, '%') AND s.isActive = true")
    List<CodeSnippet> findByOwnerAndTag(@Param("ownerId") String ownerId, @Param("tag") String tag);
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.ownerId = :ownerId AND s.usageCount > :minUsage ORDER BY s.usageCount DESC")
    List<CodeSnippet> findMostUsedSnippetsByOwner(@Param("ownerId") String ownerId, @Param("minUsage") int minUsage);
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.ownerId = :ownerId AND s.updatedAt >= :since ORDER BY s.updatedAt DESC")
    List<CodeSnippet> findRecentlyUpdatedSnippetsByOwner(@Param("ownerId") String ownerId, @Param("since") LocalDateTime since);
    
    @Query("SELECT s FROM CodeSnippet s WHERE s.projectId = :projectId AND s.isActive = true")
    List<CodeSnippet> findByProject(@Param("projectId") String projectId);
    
    @Query("UPDATE CodeSnippet s SET s.usageCount = s.usageCount + 1 WHERE s.id = :id")
    int incrementUsageCount(@Param("id") String id);
    
    @Query("SELECT COUNT(s) FROM CodeSnippet s WHERE s.ownerId = :ownerId AND s.isActive = true")
    long countActiveSnippetsByOwner(@Param("ownerId") String ownerId);
    
    @Query("SELECT COUNT(s) FROM CodeSnippet s WHERE s.language = :language AND s.isActive = true")
    long countSnippetsByLanguage(@Param("language") String language);
    
    boolean existsByOwnerIdAndTitle(String ownerId, String title);
    
    void deleteByOwnerIdAndId(String ownerId, String id);
}
