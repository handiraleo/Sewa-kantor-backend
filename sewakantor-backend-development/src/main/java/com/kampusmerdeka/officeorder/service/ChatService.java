package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.repsonse.ConversationResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.MessageResponse;
import com.kampusmerdeka.officeorder.dto.request.MessageRequest;
import com.kampusmerdeka.officeorder.entity.Conversation;
import com.kampusmerdeka.officeorder.entity.Message;
import com.kampusmerdeka.officeorder.entity.User;
import com.kampusmerdeka.officeorder.entity.UserCustomer;
import com.kampusmerdeka.officeorder.repository.ConversationRepository;
import com.kampusmerdeka.officeorder.repository.MessageRepository;
import com.kampusmerdeka.officeorder.util.FileDownloadUtil;
import com.kampusmerdeka.officeorder.util.Helpers;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private AuthService authService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    public ResponseEntity<Object> getCount() {
        UserCustomer me = authService.me();

        return ResponseUtil.ok("count message", ((Collection) messageRepository.findByConversationIdAndIsReadFalse(me.getId())).size());
    }

    public ResponseEntity<Object> getAllChat() {
        User me = authService.me();
        List<ConversationResponse> result = new ArrayList<>();
        conversationRepository.getConversations(me).forEach(conversationResponse -> {
            if (conversationResponse.getSenderAvatar() != null)
                conversationResponse.setSenderAvatar(
                        Helpers.resourceToBase64(FileDownloadUtil.getFileAsResource(conversationResponse.getSenderAvatar()))
                );

            result.add(conversationResponse);
        });

        return ResponseUtil.ok("list conversation", result);
    }

    public ResponseEntity<Object> getOneForAdmin(Long id) {
        Iterable<Message> messageIterable = messageRepository.findByConversationId(id);

        List<MessageResponse> result = new ArrayList<>();
        messageIterable.forEach(message -> result.add(getResponse(message)));

        return ResponseUtil.ok("list message", result);
    }

    public ResponseEntity<Object> getOneForCustomer() {
        UserCustomer me = authService.me();

        Iterable<Message> messageIterable = messageRepository.findByConversationId(me.getId());

        List<MessageResponse> result = new ArrayList<>();
        messageIterable.forEach(message -> result.add(getResponse(message)));

        return ResponseUtil.ok("list message", result);
    }

    public ResponseEntity<Object> sendMessage(MessageRequest request) {
        UserCustomer me = authService.me();

        Optional<Conversation> conversationOptional = conversationRepository.findById(me.getId());

        Conversation conversation;
        if (conversationOptional.isPresent()) {
            conversation = conversationOptional.get();
        } else {
            conversation = Conversation.builder().customer(me).build();
            conversation = conversationRepository.saveAndFlush(conversation);
        }

        Message message = Message.builder()
                .conversation(conversation)
                .text(request.getText())
                .sender(me)
                .isRead(false)
                .build();

        messageRepository.save(message);

        return ResponseUtil.ok("message sent successfully");
    }

    public ResponseEntity<Object> sendMessage(Long conversationId, MessageRequest request) {
        User me = authService.me();

        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if (conversationOptional.isEmpty()) return ResponseUtil.notFound("conversation not found");

        Conversation conversation = conversationOptional.get();
        Message message = Message.builder()
                .conversation(conversation)
                .text(request.getText())
                .sender(me)
                .isRead(false)
                .build();

        messageRepository.save(message);

        return ResponseUtil.ok("message sent successfully");
    }

    private MessageResponse getResponse(Message message) {
        UserCustomer me = authService.me();

        return MessageResponse.builder()
                .id(message.getId())
                .me(message.getConversation().getCustomer().equals(me))
                .text(message.getText())
                .isRead(message.getIsRead())
                .unixTime(message.getCreatedAt())
                .build();
    }

}

