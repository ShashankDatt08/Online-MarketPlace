package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.Message;
import com.marketplace.onlinemarketplace.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    /**
     * Deletes all messages before the specified date.
     *
     * @param beforeDate the cutoff date in ISO-8601 format (e.g., '2023-05-01T00:00:00')
     * @return the number of deleted messages
     */
    @DeleteMapping("/deleteBeforeDate")
    public ResponseEntity deleteMessagesBeforeDate(@RequestParam String beforeDate) {
        LocalDateTime dateTime = LocalDateTime.parse(beforeDate);
        long deletedCount = messageService.deleteMessagesBeforeDate(dateTime);
        return ResponseEntity.ok(deletedCount);
    }
}
