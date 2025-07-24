package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.Message;
import com.marketplace.onlinemarketplace.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

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
     * Deletes all messages with a timestamp before the specified date.
     *
     * @param date the cutoff date in ISO_DATE_TIME format (e.g., 2023-01-01T12:00:00)
     * @return the number of messages deleted
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteMessagesBeforeDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        long deletedCount = messageService.deleteMessagesBeforeDate(date);
        return ResponseEntity.ok(deletedCount);
    }
}
