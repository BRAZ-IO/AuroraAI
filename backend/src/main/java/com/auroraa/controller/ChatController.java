package com.auroraa.controller;

import com.auroraa.dto.ChatRequest;
import com.auroraa.dto.ChatResponse;
import com.auroraa.entity.ChatMessage;
import com.auroraa.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    @Autowired
    private ChatService chatService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatResponse sendMessage(@RequestBody ChatRequest request) {
        logger.info("Received chat message from user: {}, conversation: {}", 
                   request.getUserId(), request.getConversationId());
        
        try {
            ChatResponse response = chatService.processMessage(request);
            logger.info("Chat response generated successfully for conversation: {}", 
                       response.getConversationId());
            return response;
        } catch (Exception e) {
            logger.error("Error processing chat message: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/history/{userId}")
    public ResponseEntity<Page<ChatMessage>> getChatHistory(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        logger.debug("Fetching chat history for user: {}, page: {}, size: {}", 
                    userId, page, size);
        
        try {
            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            
            List<ChatMessage> messages = chatService.getChatHistory(userId);
            
            // Convert List to Page (simplified pagination)
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), messages.size());
            List<ChatMessage> pageContent = messages.subList(start, end);
            
            Page<ChatMessage> pageResult = new org.springframework.data.domain.PageImpl<>(
                pageContent, pageable, messages.size()
            );
            
            logger.info("Retrieved {} chat messages for user: {}", 
                       pageResult.getTotalElements(), userId);
            
            return ResponseEntity.ok(pageResult);
            
        } catch (Exception e) {
            logger.error("Error fetching chat history for user {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<ChatMessage>> getConversation(@PathVariable String conversationId) {
        logger.debug("Fetching conversation: {}", conversationId);
        
        try {
            // TODO: Implement conversation-specific retrieval in ChatService
            List<ChatMessage> messages = chatService.getRecentMessages("temp-user", 50);
            logger.info("Retrieved {} messages for conversation: {}", 
                       messages.size(), conversationId);
            return ResponseEntity.ok(messages);
            
        } catch (Exception e) {
            logger.error("Error fetching conversation {}: {}", conversationId, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/search/{userId}")
    public ResponseEntity<List<ChatMessage>> searchMessages(
            @PathVariable String userId,
            @RequestParam String query,
            @RequestParam(defaultValue = "10") int limit) {
        
        logger.debug("Searching messages for user: {}, query: '{}', limit: {}", 
                    userId, query, limit);
        
        try {
            List<ChatMessage> messages = chatService.getRecentMessages(userId, limit);
            
            // Simple text filtering (TODO: implement proper search in service)
            List<ChatMessage> filtered = messages.stream()
                .filter(msg -> msg.getMessage().toLowerCase().contains(query.toLowerCase()) ||
                              msg.getResponse().toLowerCase().contains(query.toLowerCase()))
                .limit(limit)
                .toList();
            
            logger.info("Found {} matching messages for user: {}", filtered.size(), userId);
            return ResponseEntity.ok(filtered);
            
        } catch (Exception e) {
            logger.error("Error searching messages for user {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
    
    @DeleteMapping("/history/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearChatHistory(@PathVariable String userId) {
        logger.info("Clearing chat history for user: {}", userId);
        
        try {
            chatService.deleteOldMessages(userId, 0); // Delete all messages
            logger.info("Chat history cleared for user: {}", userId);
            
        } catch (Exception e) {
            logger.error("Error clearing chat history for user {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/stats/{userId}")
    public ResponseEntity<ChatStats> getChatStats(@PathVariable String userId) {
        logger.debug("Getting chat statistics for user: {}", userId);
        
        try {
            long messageCount = chatService.getMessageCount(userId);
            List<ChatMessage> recentMessages = chatService.getRecentMessages(userId, 1);
            
            ChatStats stats = new ChatStats();
            stats.setUserId(userId);
            stats.setTotalMessages(messageCount);
            stats.setLastActivity(recentMessages.isEmpty() ? null : recentMessages.get(0).getTimestamp());
            stats.setAverageResponseTime(150); // Mock value in milliseconds
            
            logger.info("Retrieved chat stats for user: {} ({} messages)", userId, messageCount);
            return ResponseEntity.ok(stats);
            
        } catch (Exception e) {
            logger.error("Error getting chat stats for user {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
    
    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<ConversationSummary>> getConversations(@PathVariable String userId) {
        logger.debug("Getting conversations for user: {}", userId);
        
        try {
            // TODO: Implement conversation grouping in ChatService
            List<ChatMessage> messages = chatService.getChatHistory(userId);
            
            // Mock conversation summaries
            List<ConversationSummary> conversations = List.of(
                new ConversationSummary("conv-1", "General Discussion", messages.size(), 
                                      messages.isEmpty() ? null : messages.get(0).getTimestamp()),
                new ConversationSummary("conv-2", "Code Help", 5, java.time.LocalDateTime.now().minusHours(2))
            );
            
            logger.info("Retrieved {} conversations for user: {}", conversations.size(), userId);
            return ResponseEntity.ok(conversations);
            
        } catch (Exception e) {
            logger.error("Error getting conversations for user {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }
    
    // Inner classes for response DTOs
    public static class ChatStats {
        private String userId;
        private long totalMessages;
        private java.time.LocalDateTime lastActivity;
        private long averageResponseTime; // in milliseconds
        
        // Getters and setters
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public long getTotalMessages() { return totalMessages; }
        public void setTotalMessages(long totalMessages) { this.totalMessages = totalMessages; }
        
        public java.time.LocalDateTime getLastActivity() { return lastActivity; }
        public void setLastActivity(java.time.LocalDateTime lastActivity) { this.lastActivity = lastActivity; }
        
        public long getAverageResponseTime() { return averageResponseTime; }
        public void setAverageResponseTime(long averageResponseTime) { this.averageResponseTime = averageResponseTime; }
    }
    
    public static class ConversationSummary {
        private String conversationId;
        private String title;
        private int messageCount;
        private java.time.LocalDateTime lastMessageAt;
        
        public ConversationSummary() {}
        
        public ConversationSummary(String conversationId, String title, int messageCount, 
                                 java.time.LocalDateTime lastMessageAt) {
            this.conversationId = conversationId;
            this.title = title;
            this.messageCount = messageCount;
            this.lastMessageAt = lastMessageAt;
        }
        
        // Getters and setters
        public String getConversationId() { return conversationId; }
        public void setConversationId(String conversationId) { this.conversationId = conversationId; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public int getMessageCount() { return messageCount; }
        public void setMessageCount(int messageCount) { this.messageCount = messageCount; }
        
        public java.time.LocalDateTime getLastMessageAt() { return lastMessageAt; }
        public void setLastMessageAt(java.time.LocalDateTime lastMessageAt) { this.lastMessageAt = lastMessageAt; }
    }
}
