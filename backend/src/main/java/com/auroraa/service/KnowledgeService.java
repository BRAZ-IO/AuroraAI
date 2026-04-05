package com.auroraa.service;

import com.auroraa.dto.KnowledgeRequest;
import com.auroraa.dto.KnowledgeResponse;
import com.auroraa.entity.KnowledgeBase;
import com.auroraa.repository.KnowledgeBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KnowledgeService implements KnowledgeServiceInterface {
    
    @Autowired
    private KnowledgeBaseRepository knowledgeBaseRepository;
    
    @Override
    public KnowledgeResponse addKnowledge(KnowledgeRequest request) {
        if (knowledgeBaseRepository.existsByTopic(request.getTopic())) {
            throw new IllegalArgumentException("Knowledge with topic '" + request.getTopic() + "' already exists");
        }
        
        KnowledgeBase knowledge = new KnowledgeBase(
            request.getTopic(),
            request.getResponse(),
            request.getCategory()
        );
        
        KnowledgeBase saved = knowledgeBaseRepository.save(knowledge);
        
        return new KnowledgeResponse(
            saved.getId(),
            saved.getTopic(),
            saved.getResponse(),
            saved.getCategory(),
            saved.getCreatedAt(),
            "Knowledge added successfully"
        );
    }
    
    @Override
    public Optional<KnowledgeBase> getKnowledgeByTopic(String topic) {
        return knowledgeBaseRepository.findByTopic(topic);
    }
    
    @Override
    public List<KnowledgeBase> getKnowledgeByCategory(String category) {
        return knowledgeBaseRepository.findByCategoryOrderByCreatedAtDesc(category);
    }
    
    @Override
    public List<String> getAllCategories() {
        return knowledgeBaseRepository.findAllCategories();
    }
    
    @Override
    public List<KnowledgeBase> searchKnowledge(String searchTerm) {
        return knowledgeBaseRepository.searchByTopicOrResponse(searchTerm);
    }
    
    @Override
    public KnowledgeResponse updateKnowledge(String id, KnowledgeRequest request) {
        Optional<KnowledgeBase> existingOpt = knowledgeBaseRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Knowledge not found with id: " + id);
        }
        
        KnowledgeBase existing = existingOpt.get();
        
        // Check if topic is being changed and if new topic already exists
        if (!existing.getTopic().equals(request.getTopic()) && 
            knowledgeBaseRepository.existsByTopic(request.getTopic())) {
            throw new IllegalArgumentException("Knowledge with topic '" + request.getTopic() + "' already exists");
        }
        
        existing.setTopic(request.getTopic());
        existing.setResponse(request.getResponse());
        existing.setCategory(request.getCategory());
        
        KnowledgeBase updated = knowledgeBaseRepository.save(existing);
        
        return new KnowledgeResponse(
            updated.getId(),
            updated.getTopic(),
            updated.getResponse(),
            updated.getCategory(),
            updated.getCreatedAt(),
            "Knowledge updated successfully"
        );
    }
    
    @Override
    public void deleteKnowledge(String id) {
        if (!knowledgeBaseRepository.existsById(id)) {
            throw new IllegalArgumentException("Knowledge not found with id: " + id);
        }
        knowledgeBaseRepository.deleteById(id);
    }
    
    @Override
    public List<KnowledgeBase> getAllKnowledge() {
        return knowledgeBaseRepository.findAll();
    }
}
