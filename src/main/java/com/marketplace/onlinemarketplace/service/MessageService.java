package com.marketplace.onlinemarketplace.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.entity.Conversation;
import com.marketplace.onlinemarketplace.entity.Message;
import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.repository.BidRepo;
import com.marketplace.onlinemarketplace.mongoRepo.ConversationRepo;
import com.marketplace.onlinemarketplace.mongoRepo.MessageRepo;
import com.marketplace.onlinemarketplace.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    @Autowired
    private AmazonSQS amazonSQS;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private BidRepo bidRepo;

    @Value("${AWS_SQS_QUEUE_URL}")
    private String queueUrl;

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        List<Projects> senderProjects = projectRepo.findByClientId(senderId);
        senderProjects.addAll(projectRepo.findByFreelancerId(senderId));

        Projects project = senderProjects.stream()
                .filter(p -> p.getClientId().equals(receiverId) || p.getId().equals(receiverId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found or sender is not part of the project"));

        Bid bid = bidRepo.findByProjectIdAndStatus(project.getId(), Bid.BidStatus.ACCEPTED)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No accepted bid for this project"));

        if (!senderId.equals(project.getClientId()) && !senderId.equals(bid.getFreelancerId())) {
            throw new RuntimeException("Sender is not part of this project");
        }

        if (!receiverId.equals(project.getClientId()) && !receiverId.equals(bid.getFreelancerId())) {
            throw new RuntimeException("Receiver is not part of this project");
        }

        Conversation conversation = conversationRepo.findByUserId1AndUserId2AndProjectId(
                        Math.min(senderId, receiverId), Math.max(senderId, receiverId), project.getId())
                .orElseGet(() -> createConversation(senderId, receiverId, project.getId()));

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setStatus(Message.Status.SENT);
        message.setConversationId(conversation.getId());
        Message savedMessage = messageRepo.save(message);


        conversation.setLastMessageTimestamp(LocalDateTime.now());
        conversationRepo.save(conversation);

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(String.format("Message from user %d to user %d in project %d: %s",
                        senderId, receiverId, project.getId(), content));
        amazonSQS.sendMessage(sendMessageRequest);

        return savedMessage;
    }

    private Conversation createConversation(Long senderId, Long receiverId, Long projectId) {
        Conversation conversation = new Conversation();
        conversation.setUserId1(Math.min(senderId, receiverId));
        conversation.setUserId2(Math.max(senderId, receiverId));
        conversation.setProjectId(projectId);
        conversation.setLastMessageTimestamp(LocalDateTime.now());
        return conversationRepo.save(conversation);
    }


    public List<Message> getConversationById(Long projectId, Long userId) {
        Projects projects = projectRepo.findById(projectId).get();

        Bid bid = bidRepo.findByProjectIdAndStatus(projectId, Bid.BidStatus.ACCEPTED)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No accepted bid for this project"));


        if(!userId.equals(projects.getClientId()) && !userId.equals(bid.getFreelancerId())) {
            throw  new RuntimeException("User is not part of this project");
        }

        Optional<Conversation> conversation = conversationRepo.findByUserId1AndUserId2AndProjectId(
                Math.min(projects.getClientId(), bid.getFreelancerId()),
                Math.max(projects.getClientId(), bid.getFreelancerId()),
                projectId
        );


        if (conversation.isEmpty()) {
            throw new RuntimeException("Conversation not found.");
        }

        return messageRepo.findByConversationIdOrderByTimestampAsc(conversation.get().getId());
    }

    /**
     * Deletes messages older than the specified date.
     *
     * @param beforeDate date threshold; messages before this date will be deleted
     */
    public void deleteMessagesBefore(LocalDateTime beforeDate) {
        messageRepo.deleteByTimestampBefore(beforeDate);
    }
}
