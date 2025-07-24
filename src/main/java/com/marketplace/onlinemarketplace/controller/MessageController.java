package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.Message;
import com.marketplace.onlinemarketplace.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PutMapping("/send")
    private ResponseEntity sendMessage(@RequestParam Long senderId,
                                       @RequestParam Long receiverId,
                                       @RequestParam String content) {

       Message message = messageService.sendMessage(senderId,receiverId,content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversations")
    public ResponseEntity getConversations(@RequestParam Long projectId , @RequestParam Long userId) {
        List<Message> message = messageService.getConversationById(projectId , userId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{date}")
    public ResponseEntity<String> deleteMessagesBeforeDate(@PathVariable String date) {
        try {
            LocalDateTime cutoff = LocalDateTime.parse(date);
            messageService.deleteMessagesBefore(cutoff);
            return ResponseEntity.ok("Messages deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting messages: " + e.getMessage());
        }
    }
}
