package com.kampusmerdeka.officeorder.controller.admin;

import com.kampusmerdeka.officeorder.dto.request.MessageRequest;
import com.kampusmerdeka.officeorder.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/admin/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<Object> getAllChat() {
        return chatService.getAllChat();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Object> getOne(@PathVariable(name = "id") Long id) {
        return chatService.getOneForAdmin(id);
    }

    @PostMapping(value = "{id}/message")
    public ResponseEntity<Object> sendMessage(@PathVariable(name = "id") Long id, @Valid @RequestBody MessageRequest request) {
        return chatService.sendMessage(id, request);
    }
}
