package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Iterable<Message> findByConversationIdAndIsReadFalse(Long userCustomer);

    Iterable<Message> findByConversationId(Long id);
}