package com.auroraa.controller;

import com.auroraa.dto.KnowledgeRequest;
import com.auroraa.dto.KnowledgeResponse;
import com.auroraa.entity.KnowledgeBase;
import com.auroraa.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
@CrossOrigin(origins = "*")
public class KnowledgeController {
    
    @Autowired
    private KnowledgeService knowledgeService;
    
    @PostMapping
    public ResponseEntity<KnowledgeResponse> addKnowledge(@RequestBody KnowledgeRequest request) {
        try {
            KnowledgeResponse response = knowledgeService.addKnowledge(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/topic/{topic}")
    public ResponseEntity<KnowledgeBase> getKnowledgeByTopic(@PathVariable String topic) {
        try {
            return knowledgeService.getKnowledgeByTopic(topic)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<KnowledgeBase>> getKnowledgeByCategory(@PathVariable String category) {
        try {
            List<KnowledgeBase> knowledge = knowledgeService.getKnowledgeByCategory(category);
            return ResponseEntity.ok(knowledge);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        try {
            List<String> categories = knowledgeService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<KnowledgeBase>> searchKnowledge(@RequestParam String term) {
        try {
            List<KnowledgeBase> results = knowledgeService.searchKnowledge(term);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<KnowledgeBase>> getAllKnowledge() {
        try {
            List<KnowledgeBase> knowledge = knowledgeService.getAllKnowledge();
            return ResponseEntity.ok(knowledge);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeResponse> updateKnowledge(
            @PathVariable String id, 
            @RequestBody KnowledgeRequest request) {
        try {
            KnowledgeResponse response = knowledgeService.updateKnowledge(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledge(@PathVariable String id) {
        try {
            knowledgeService.deleteKnowledge(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
