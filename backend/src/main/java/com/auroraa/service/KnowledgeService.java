package com.auroraa.service;

import com.auroraa.dto.KnowledgeRequest;
import com.auroraa.dto.KnowledgeResponse;
import com.auroraa.entity.KnowledgeBase;
import com.auroraa.exception.BusinessException;
import com.auroraa.exception.ResourceNotFoundException;
import com.auroraa.exception.ValidationException;
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
        validateKnowledgeRequest(request);
        
        if (knowledgeBaseRepository.existsByTopic(request.getTopic())) {
            throw new BusinessException("Knowledge with topic '" + request.getTopic() + "' already exists");
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
        if (topic == null || topic.trim().isEmpty()) {
            throw new ValidationException("Topic cannot be null or empty");
        }
        return knowledgeBaseRepository.findByTopic(topic);
    }
    
    @Override
    public List<KnowledgeBase> getKnowledgeByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new ValidationException("Category cannot be null or empty");
        }
        return knowledgeBaseRepository.findByCategoryOrderByCreatedAtDesc(category);
    }
    
    @Override
    public List<String> getAllCategories() {
        List<String> categories = knowledgeBaseRepository.findAllCategories();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        return categories;
    }
    
    @Override
    public List<KnowledgeBase> searchKnowledge(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new ValidationException("Search term cannot be null or empty");
        }
        if (searchTerm.length() < 2) {
            throw new ValidationException("Search term must be at least 2 characters long");
        }
        
        List<KnowledgeBase> results = knowledgeBaseRepository.searchByTopicOrResponse(searchTerm);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No knowledge found for search term: " + searchTerm);
        }
        return results;
    }
    
    @Override
    public KnowledgeResponse updateKnowledge(String id, KnowledgeRequest request) {
        validateKnowledgeRequest(request);
        
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Knowledge ID cannot be null or empty");
        }
        
        Optional<KnowledgeBase> existingOpt = knowledgeBaseRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new ResourceNotFoundException("Knowledge", "id", id);
        }
        
        KnowledgeBase existing = existingOpt.get();
        
        // Check if topic is being changed and if new topic already exists
        if (!existing.getTopic().equals(request.getTopic()) && 
            knowledgeBaseRepository.existsByTopic(request.getTopic())) {
            throw new BusinessException("Knowledge with topic '" + request.getTopic() + "' already exists");
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
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Knowledge ID cannot be null or empty");
        }
        
        if (!knowledgeBaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Knowledge", "id", id);
        }
        knowledgeBaseRepository.deleteById(id);
    }
    
    @Override
    public List<KnowledgeBase> getAllKnowledge() {
        List<KnowledgeBase> knowledge = knowledgeBaseRepository.findAll();
        if (knowledge.isEmpty()) {
            throw new ResourceNotFoundException("No knowledge found in the database");
        }
        return knowledge;
    }
    
    private void validateKnowledgeRequest(KnowledgeRequest request) {
        if (request == null) {
            throw new ValidationException("Knowledge request cannot be null");
        }
        if (request.getTopic() == null || request.getTopic().trim().isEmpty()) {
            throw new ValidationException("Topic cannot be null or empty");
        }
        if (request.getTopic().length() > 200) {
            throw new ValidationException("Topic cannot exceed 200 characters");
        }
        if (request.getResponse() == null || request.getResponse().trim().isEmpty()) {
            throw new ValidationException("Response cannot be null or empty");
        }
        if (request.getResponse().length() > 2000) {
            throw new ValidationException("Response cannot exceed 2000 characters");
        }
        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new ValidationException("Category cannot be null or empty");
        }
        if (request.getCategory().length() > 100) {
            throw new ValidationException("Category cannot exceed 100 characters");
        }
    }
}
