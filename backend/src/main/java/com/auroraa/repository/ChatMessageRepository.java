package com.auroraa.repository;

import com.auroraa.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    
    List<ChatMessage> findByUserIdOrderByTimestampDesc(String userId);
    
    List<ChatMessage> findByUserIdAndTimestampAfter(String userId, LocalDateTime timestamp);
    
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.userId = :userId ORDER BY cm.timestamp DESC")
    List<ChatMessage> findRecentMessagesByUser(@Param("userId") String userId);
    
    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.userId = :userId AND cm.timestamp >= :since")
    long countMessagesByUserSince(@Param("userId") String userId, @Param("since") LocalDateTime since);
    
    void deleteByUserIdAndTimestampBefore(String userId, LocalDateTime timestamp);
}
