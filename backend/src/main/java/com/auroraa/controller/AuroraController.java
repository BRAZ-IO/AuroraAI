package com.auroraa.controller;

import com.auroraa.dto.ChatRequest;
import com.auroraa.dto.ChatResponse;
import com.auroraa.entity.ChatHistory;
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
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return chatService.processMessage(request);
    }
    
    @GetMapping("/chat/history/{userId}")
    public List<ChatHistory> getChatHistory(@PathVariable String userId) {
        return chatService.getChatHistory(userId);
    }
    
    @GetMapping("/chat/recent/{userId}")
    public List<ChatHistory> getRecentMessages(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit) {
        return chatService.getRecentMessages(userId, limit);
    }
    
    @GetMapping("/chat/count/{userId}")
    public long getMessageCount(@PathVariable String userId) {
        return chatService.getMessageCount(userId);
    }
}
