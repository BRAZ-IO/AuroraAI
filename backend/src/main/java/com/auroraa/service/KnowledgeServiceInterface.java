package com.auroraa.service;

import com.auroraa.dto.KnowledgeRequest;
import com.auroraa.dto.KnowledgeResponse;
import com.auroraa.entity.KnowledgeBase;

import java.util.List;
import java.util.Optional;

public interface KnowledgeServiceInterface {
    
    KnowledgeResponse addKnowledge(KnowledgeRequest request);
    
    Optional<KnowledgeBase> getKnowledgeByTopic(String topic);
    
    List<KnowledgeBase> getKnowledgeByCategory(String category);
    
    List<String> getAllCategories();
    
    List<KnowledgeBase> searchKnowledge(String searchTerm);
    
    KnowledgeResponse updateKnowledge(String id, KnowledgeRequest request);
    
    void deleteKnowledge(String id);
    
    List<KnowledgeBase> getAllKnowledge();
}
