package com.kampusmerdeka.officeorder.graphql;

import com.kampusmerdeka.officeorder.dto.repsonse.MessageResponse;
import com.kampusmerdeka.officeorder.service.GraphQLChatService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//@Component
public class ChatSubscription {
    @Autowired
    private GraphQLChatService chatService;

    public Publisher<List<MessageResponse>> messagesForAdmin(@Argument("conversationId") Long conversationId) {
        return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            List<MessageResponse> messages = chatService.getMessagesByConversationId(conversationId);
            subscriber.onNext(messages);
            subscriber.onComplete();
        }, 0, 2, TimeUnit.SECONDS);
    }
}
