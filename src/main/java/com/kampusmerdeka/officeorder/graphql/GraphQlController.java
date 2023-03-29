package com.kampusmerdeka.officeorder.graphql;

import com.kampusmerdeka.officeorder.dto.repsonse.ConversationResponse;
import com.kampusmerdeka.officeorder.dto.repsonse.MessageResponse;
import com.kampusmerdeka.officeorder.dto.request.MessageRequest;
import com.kampusmerdeka.officeorder.service.GraphQLChatService;
import com.netflix.graphql.dgs.DgsSubscription;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Controller
public class GraphQlController {
    @Autowired
    private GraphQLChatService chatService;

    @QueryMapping(name = "getAllConversations")
    public List<ConversationResponse> getAllConversations() {
        return chatService.getConversations();
    }

    @QueryMapping(name = "getMessagesForCustomer")
    public List<MessageResponse> getMessagesForCustomer() {
        return chatService.getMessagesForCustomer();
    }

    @QueryMapping(name = "getMessagesForAdmin")
    public List<MessageResponse> getMessagesForAdmin(@Argument("conversationId") Long conversationId) {
        return chatService.getMessagesByConversationId(conversationId);
    }

    @MutationMapping(name = "customerSendMessage")
    public MessageResponse customerSendMessage(@Argument("input") MessageRequest request) {
        return chatService.sendMessage(request);
    }

    @MutationMapping(name = "adminSendMessage")
    public MessageResponse adminSendMessage(@Argument("input") MessageRequest request) {
        return chatService.sendMessage(request);
    }

    //    @SubscriptionMapping(name = "messagesForAdmin")
    @DgsSubscription
    public Publisher<List<MessageResponse>> messagesForAdmin(@Argument("conversationId") Long conversationId) {
        List<MessageResponse> messages = chatService.getMessagesByConversationId(conversationId);
        return Flux.interval(Duration.ofSeconds(1)).map(t -> messages);

//        return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
//            List<MessageResponse> messages = chatService.getMessagesByConversationId(conversationId);
//            subscriber.onNext(messages);
//            subscriber.onComplete();
//        }, 0, 2, TimeUnit.SECONDS);
    }
}
