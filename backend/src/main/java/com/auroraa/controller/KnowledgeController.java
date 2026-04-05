package com.auroraa.controller;

import com.auroraa.dto.KnowledgeRequest;
import com.auroraa.dto.KnowledgeResponse;
import com.auroraa.entity.KnowledgeBase;
import com.auroraa.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/knowledge")
@CrossOrigin(origins = "*")
public class KnowledgeController {
    
    @Autowired
    private KnowledgeService knowledgeService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KnowledgeResponse addKnowledge(@RequestBody KnowledgeRequest request) {
        return knowledgeService.addKnowledge(request);
    }
    
    @GetMapping("/topic/{topic}")
    public ResponseEntity<KnowledgeBase> getKnowledgeByTopic(@PathVariable String topic) {
        return knowledgeService.getKnowledgeByTopic(topic)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/category/{category}")
    public List<KnowledgeBase> getKnowledgeByCategory(@PathVariable String category) {
        return knowledgeService.getKnowledgeByCategory(category);
    }
    
    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return knowledgeService.getAllCategories();
    }
    
    @GetMapping("/search")
    public List<KnowledgeBase> searchKnowledge(@RequestParam String term) {
        return knowledgeService.searchKnowledge(term);
    }
    
    @GetMapping
    public List<KnowledgeBase> getAllKnowledge() {
        return knowledgeService.getAllKnowledge();
    }
    
    @PutMapping("/{id}")
    public KnowledgeResponse updateKnowledge(
            @PathVariable String id, 
            @RequestBody KnowledgeRequest request) {
        return knowledgeService.updateKnowledge(id, request);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKnowledge(@PathVariable String id) {
        knowledgeService.deleteKnowledge(id);
    }
}
