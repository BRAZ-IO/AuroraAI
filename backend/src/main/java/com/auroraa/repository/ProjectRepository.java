package com.auroraa.repository;

import com.auroraa.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    
    List<Project> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
    
    List<Project> findByOwnerIdAndCategoryOrderByCreatedAtDesc(String ownerId, String category);
    
    List<Project> findByVisibilityAndIsActiveOrderByCreatedAtDesc(String visibility, boolean isActive);
    
    List<Project> findByCategoryAndIsActiveOrderByCreatedAtDesc(String category, boolean isActive);
    
    Optional<Project> findByIdAndOwnerId(String id, String ownerId);
    
    @Query("SELECT p FROM Project p WHERE p.ownerId = :ownerId AND p.isActive = true AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Project> searchProjectsByOwner(@Param("ownerId") String ownerId, @Param("query") String query);
    
    @Query("SELECT p FROM Project p WHERE p.isActive = true AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Project> searchProjects(@Param("query") String query);
    
    @Query("SELECT DISTINCT p.category FROM Project p WHERE p.isActive = true")
    List<String> findAllCategories();
    
    @Query("SELECT COUNT(p) FROM Project p WHERE p.ownerId = :ownerId AND p.isActive = true")
    long countActiveProjectsByOwner(@Param("ownerId") String ownerId);
    
    @Query("SELECT p FROM Project p WHERE p.ownerId = :ownerId AND p.updatedAt >= :since")
    List<Project> findRecentlyUpdatedProjectsByOwner(@Param("ownerId") String ownerId, @Param("since") LocalDateTime since);
    
    @Query("SELECT p FROM Project p WHERE p.fileCount > :minFiles ORDER BY p.fileCount DESC")
    List<Project> findProjectsByMinFileCount(@Param("minFiles") int minFiles);
    
    @Query("SELECT p FROM Project p WHERE p.totalSize > :minSize ORDER BY p.totalSize DESC")
    List<Project> findProjectsByMinSize(@Param("minSize") long minSize);
    
    boolean existsByOwnerIdAndName(String ownerId, String name);
    
    void deleteByOwnerIdAndId(String ownerId, String id);
    
    @Query("UPDATE Project p SET p.fileCount = p.fileCount + :count WHERE p.id = :id")
    int incrementFileCount(@Param("id") String id, @Param("count") int count);
    
    @Query("UPDATE Project p SET p.totalSize = p.totalSize + :size WHERE p.id = :id")
    int incrementTotalSize(@Param("id") String id, @Param("size") long size);
    
    @Query("UPDATE Project p SET p.fileCount = p.fileCount - :count WHERE p.id = :id AND p.fileCount >= :count")
    int decrementFileCount(@Param("id") String id, @Param("count") int count);
    
    @Query("UPDATE Project p SET p.totalSize = GREATEST(p.totalSize - :size, 0) WHERE p.id = :id")
    int decrementTotalSize(@Param("id") String id, @Param("size") long size);
}
