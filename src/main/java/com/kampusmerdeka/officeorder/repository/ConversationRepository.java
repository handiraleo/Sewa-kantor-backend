package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.dto.repsonse.ConversationResponse;
import com.kampusmerdeka.officeorder.entity.Conversation;
import com.kampusmerdeka.officeorder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
        @Query("SELECT new com.kampusmerdeka.officeorder.dto.repsonse.ConversationResponse(" +
            "c.id, " +
            "MAX(m.text), " +
            "CONCAT(MAX(m.sender.firstName), ' ', MAX(m.sender.lastName)), " +
            "MAX(m.sender.avatar), " +
            "CAST(SUM(CASE WHEN m.isRead = FALSE AND m.sender.id != :#{#me.id} THEN 1 ELSE 0 END) AS int), " +
            "MAX(m.createdAt)" +
            ") " +
            "FROM Conversation c " +
            "INNER JOIN Message m ON m.conversation = c " +
            "GROUP BY c, c.customer.firstName, c.customer.lastName, c.customer.avatar")
    List<ConversationResponse> getConversations(@Param("me") User me);
}