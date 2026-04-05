package com.auroraa.repository;

import com.auroraa.entity.KnowledgeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, String> {
    
    Optional<KnowledgeBase> findByTopic(String topic);
    
    List<KnowledgeBase> findByCategoryIgnoreCase(String category);
    
    @Query("SELECT kb FROM KnowledgeBase kb WHERE kb.category = :category ORDER BY kb.createdAt DESC")
    List<KnowledgeBase> findByCategoryOrderByCreatedAtDesc(@Param("category") String category);
    
    @Query("SELECT DISTINCT kb.category FROM KnowledgeBase kb")
    List<String> findAllCategories();
    
    @Query("SELECT kb FROM KnowledgeBase kb WHERE LOWER(kb.topic) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(kb.response) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<KnowledgeBase> searchByTopicOrResponse(@Param("searchTerm") String searchTerm);
    
    boolean existsByTopic(String topic);
}
