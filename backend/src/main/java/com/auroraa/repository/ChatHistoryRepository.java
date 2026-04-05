package com.auroraa.repository;

import com.auroraa.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, String> {
    
    List<ChatHistory> findByUserIdOrderByTimestampDesc(String userId);
    
    List<ChatHistory> findByUserIdAndTimestampAfter(String userId, LocalDateTime timestamp);
    
    @Query("SELECT cm FROM ChatHistory cm WHERE cm.userId = :userId ORDER BY cm.timestamp DESC")
    List<ChatHistory> findRecentMessagesByUser(@Param("userId") String userId);
    
    @Query("SELECT COUNT(cm) FROM ChatHistory cm WHERE cm.userId = :userId AND cm.timestamp >= :since")
    long countMessagesByUserSince(@Param("userId") String userId, @Param("since") LocalDateTime since);
    
    void deleteByUserIdAndTimestampBefore(String userId, LocalDateTime timestamp);
}
