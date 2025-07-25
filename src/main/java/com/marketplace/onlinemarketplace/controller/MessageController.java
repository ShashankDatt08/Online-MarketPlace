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
     * Deletes messages older than the specified date.
     *
     * @param date ISO-8601 date-time string; messages before this date will be deleted
     * @return empty response on success
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMessagesBefore(@RequestParam String date) {
        LocalDateTime beforeDate = LocalDateTime.parse(date);
        messageService.deleteMessagesBefore(beforeDate);
        return ResponseEntity.ok().build();
    }
}
