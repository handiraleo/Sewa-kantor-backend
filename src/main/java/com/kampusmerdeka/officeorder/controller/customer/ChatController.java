package com.kampusmerdeka.officeorder.controller.customer;

import com.kampusmerdeka.officeorder.dto.request.MessageRequest;
import com.kampusmerdeka.officeorder.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/customer/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<Object> getConversation() {
        return chatService.getOneForCustomer();
    }

    @PostMapping
    public ResponseEntity<Object> sendMessage(@Valid @RequestBody MessageRequest request) {
        return chatService.sendMessage(request);
    }
}
