package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.repsonse.ConversationResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.MessageResponse;
import com.kampusmerdeka.officeorder.dto.request.MessageRequest;
import com.kampusmerdeka.officeorder.entity.*;
import com.kampusmerdeka.officeorder.repository.ConversationRepository;
import com.kampusmerdeka.officeorder.repository.MessageRepository;
import com.kampusmerdeka.officeorder.util.FileDownloadUtil;
import com.kampusmerdeka.officeorder.util.Helpers;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GraphQLChatService {
    @Autowired
    private AuthService authService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    public Integer getCount() {
        UserCustomer me = authService.me();

        return ((Collection) messageRepository.findByConversationIdAndIsReadFalse(me.getId())).size();
    }

    public List<ConversationResponse> getConversations() {
        User me = authService.me();
        List<ConversationResponse> result = new ArrayList<>();
        conversationRepository.getConversations(me).forEach(conversationResponse -> {
            if (conversationResponse.getSenderAvatar() != null)
                conversationResponse.setSenderAvatar(
                        Helpers.resourceToBase64(FileDownloadUtil.getFileAsResource(conversationResponse.getSenderAvatar()))
                );

            result.add(conversationResponse);
        });

        return result;
    }

    public List<ConversationResponse> getCustomerConversation() {
        User me = authService.me();
        List<ConversationResponse> result = new ArrayList<>();
        conversationRepository.getConversations(me).forEach(conversationResponse -> {
            if (conversationResponse.getSenderAvatar() != null)
                conversationResponse.setSenderAvatar(
                        Helpers.resourceToBase64(FileDownloadUtil.getFileAsResource(conversationResponse.getSenderAvatar()))
                );

            result.add(conversationResponse);
        });

        return result;
    }

    public List<MessageResponse> getMessagesForCustomer() {
        UserCustomer me = authService.me();

        Iterable<Message> messageIterable = messageRepository.findByConversationId(me.getId());

        List<MessageResponse> result = new ArrayList<>();
        messageIterable.forEach(message -> result.add(getResponse(message)));

        return result;
    }

    public List<MessageResponse> getMessagesByConversationId(Long conversationId) {
        Iterable<Message> messageIterable = messageRepository.findByConversationId(conversationId);

        List<MessageResponse> result = new ArrayList<>();
        messageIterable.forEach(message -> result.add(getResponse(message)));

        return result;
    }

    public Publisher<MessageResponse> getMessagesStreamByConversationId(Long conversationId) {
        Iterable<Message> messageIterable = messageRepository.findByConversationId(conversationId);

        List<MessageResponse> result = new ArrayList<>();
        messageIterable.forEach(message -> result.add(getResponse(message)));

        return (Publisher<MessageResponse>) result;
    }

    public MessageResponse sendMessage(MessageRequest request) {
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

        message = messageRepository.saveAndFlush(message);

        return getResponse(message);
    }

    public MessageResponse sendMessage(Long conversationId, MessageRequest request) {
        UserAdmin me = authService.me();

        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        Conversation conversation = conversationOptional.get();

        Message message = Message.builder()
                .conversation(conversation)
                .text(request.getText())
                .sender(me)
                .isRead(false)
                .build();

        message = messageRepository.saveAndFlush(message);

        return getResponse(message);
    }

    private MessageResponse getResponse(Message message) {
        User me = authService.me();

        return MessageResponse.builder()
                .id(message.getId())
                .me(message.getSender().equals(me))
                .text(message.getText())
                .isRead(message.getIsRead())
                .unixTime(message.getCreatedAt())
                .build();
    }

}
