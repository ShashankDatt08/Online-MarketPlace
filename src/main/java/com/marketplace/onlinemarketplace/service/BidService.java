import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
package com.marketplace.onlinemarketplace.service;


import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.entity.Conversation;
import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.BidRepo;
import com.marketplace.onlinemarketplace.mongoRepo.ConversationRepo;
import com.marketplace.onlinemarketplace.repository.ProjectRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    public Bid createBids(Long projectId, Long freelancerId, Long amount, String proposal) {
        Projects project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        if(project.getStatus() == Projects.Status.CLOSED || project.getStatus() == Projects.Status.INPROGRESS){
            throw new RuntimeException("Can't place bids as the project is not available");
        }

        Optional<User> user = userRepo.findById(freelancerId);
        if (user.get().getRole() != User.Role.FREELANCER) {
            throw new RuntimeException("Only Freelancers can place bids");
        }

        Bid bid = new Bid();
        bid.setProjectId(projectId);
        bid.setFreelancerId(freelancerId);
        bid.setAmount(amount);
        bid.setProposal(proposal);
        bid.setBidDate(LocalDateTime.now());
        bid.setStatus(Bid.BidStatus.PENDING);

        return bidRepo.save(bid);

    }


    public List<Bid> getBidById(Long projectId, boolean isClientViewing) {
        List<Bid> bids = bidRepo.findByProjectId(projectId);

        if (!isClientViewing) {
            bids.forEach(bid -> {
                bid.setFreelancerId(null);
                bid.setFreelancerName("Anonymous User");
            });

        }
        return bids;
    }

    public Bid acceptBid(Long bidId, Long clientId) {
        Bid bid = bidRepo.findById(bidId).orElseThrow(() -> new RuntimeException("Bid not found"));
        Projects projects = projectRepo.findById(bid.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projects.getClientId().equals(clientId)) {
            throw new RuntimeException("Only Client who posted can accept bid");
        }

        List<Bid> exisitingBids = bidRepo.findByProjectIdAndStatus(bid.getProjectId(), Bid.BidStatus.ACCEPTED);
        if (!exisitingBids.isEmpty()) {
            throw new RuntimeException("Bid already accepted for this project");
        }
        bid.setStatus(Bid.BidStatus.ACCEPTED);
        projects.setStatus(Projects.Status.INPROGRESS);
        bidRepo.save(bid);

        List<Bid> otherBids = bidRepo.findByProjectIdAndStatus(bid.getProjectId(), Bid.BidStatus.PENDING);
        for (Bid otherBid : otherBids) {
            if (!otherBid.getId().equals(bidId)) {
                otherBid.setStatus(Bid.BidStatus.REJECTED);
                bidRepo.save(otherBid);
            }

        }
        return bid;
    }

    Without the actual `BidService` class, it's hard to provide specific test cases. However, I'll provide a general structure for testing a hypothetical `BidService` class that has methods for creating, updating, deleting, and retrieving bids.

    ```java

    class BidServiceTest {

        private BidService bidService = new BidService();

        @Test
        void testCreateBid() {
            Bid bid = new Bid();
            // set properties of bid
            Bid createdBid = bidService.createBid(bid);
            assertNotNull(createdBid);
            // assert other properties
        }

        @Test
        void testUpdateBid() {
            Bid bid = new Bid();
            // set properties of bid
            Bid updatedBid = bidService.updateBid(bid);
            assertNotNull(updatedBid);
            // assert other properties
        }

        @Test
        void testDeleteBid() {
            Bid bid = new Bid();
            // set properties of bid
            boolean isDeleted = bidService.deleteBid(bid);
            assertTrue(isDeleted);
        }

        @Test
        void testGetBid() {
            Bid bid = new Bid();
            // set properties of bid
            Bid retrievedBid = bidService.getBid(bid.getId());
            assertNotNull(retrievedBid);
            // assert other properties
        }
    }
    ```

    This is a basic structure for testing a `BidService` class. You would need to replace the `Bid` class and its methods with the actual ones from your application. Also, you would need to add more assertions to check if the returned objects have the expected properties.

}
