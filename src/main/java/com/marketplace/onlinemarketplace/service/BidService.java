import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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



    public class BidServiceTest {

        @InjectMocks
        private BidService bidService;

        @Mock
        private BidRepository bidRepository;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testGetBidById() {
            Bid bid = new Bid();
            bid.setId(1);
            when(bidRepository.findById(1)).thenReturn(Optional.of(bid));

            Bid result = bidService.getBidById(1);
            assertEquals(bid, result);
        }

        @Test
        public void testGetBidByIdNotFound() {
            when(bidRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(BidNotFoundException.class, () -> bidService.getBidById(1));
        }

        @Test
        public void testCreateBid() {
            Bid bid = new Bid();
            bid.setId(1);
            when(bidRepository.save(bid)).thenReturn(bid);

            Bid result = bidService.createBid(bid);
            assertEquals(bid, result);
        }

        @Test
        public void testUpdateBid() {
            Bid bid = new Bid();
            bid.setId(1);
            when(bidRepository.findById(1)).thenReturn(Optional.of(bid));
            when(bidRepository.save(bid)).thenReturn(bid);

            Bid result = bidService.updateBid(1, bid);
            assertEquals(bid, result);
        }

        @Test
        public void testUpdateBidNotFound() {
            Bid bid = new Bid();
            bid.setId(1);
            when(bidRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(BidNotFoundException.class, () -> bidService.updateBid(1, bid));
        }

        @Test
        public void testDeleteBid() {
            Bid bid = new Bid();
            bid.setId(1);
            when(bidRepository.findById(1)).thenReturn(Optional.of(bid));
            doNothing().when(bidRepository).delete(bid);

            bidService.deleteBid(1);
            verify(bidRepository, times(1)).delete(bid);
        }

        @Test
        public void testDeleteBidNotFound() {
            when(bidRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(BidNotFoundException.class, () -> bidService.deleteBid(1));
        }
    }

}
