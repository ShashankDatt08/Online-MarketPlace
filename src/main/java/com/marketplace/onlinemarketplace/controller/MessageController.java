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
    /**
     * Deletes all messages before the specified timestamp.
     *
     * @param dateISO cutoff date in ISO-8601 format (e.g., '2023-01-01T00:00:00')
     * @return number of messages deleted
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessagesBefore(@RequestParam("date") String dateISO) {
        LocalDateTime threshold = LocalDateTime.parse(dateISO);
        long deletedCount = messageService.deleteMessagesBefore(threshold);
        return ResponseEntity.ok(deletedCount);
    }

}
