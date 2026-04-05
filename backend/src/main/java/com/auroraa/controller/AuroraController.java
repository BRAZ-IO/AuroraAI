package com.auroraa.controller;

import com.auroraa.dto.ChatRequest;
import com.auroraa.dto.ChatResponse;
import com.auroraa.entity.ChatMessage;
import com.auroraa.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aurora")
@CrossOrigin(origins = "*")
public class AuroraController {
    
    @Autowired
    private ChatService chatService;
    
    @GetMapping("/status")
    public String status() {
        return "🌟 AuroraAI está online!";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "Olá! 👋 Sou AuroraAI, seu assistente inteligente!";
    }
    
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            ChatResponse response = chatService.processMessage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/chat/history/{userId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String userId) {
        try {
            List<ChatMessage> history = chatService.getChatHistory(userId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/chat/recent/{userId}")
    public ResponseEntity<List<ChatMessage>> getRecentMessages(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<ChatMessage> messages = chatService.getRecentMessages(userId, limit);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/chat/count/{userId}")
    public ResponseEntity<Long> getMessageCount(@PathVariable String userId) {
        try {
            long count = chatService.getMessageCount(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
