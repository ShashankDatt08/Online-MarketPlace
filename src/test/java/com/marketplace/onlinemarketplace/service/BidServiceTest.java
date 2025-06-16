package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.BidRepo;
import com.marketplace.onlinemarketplace.mongoRepo.ConversationRepo;
import com.marketplace.onlinemarketplace.repository.ProjectRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BidServiceTest {

    @InjectMocks
    private BidService bidService;

    @Mock
    private BidRepo bidRepo;

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ConversationRepo conversationRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateBids() {
        Long projectId = 1L;
        Long freelancerId = 1L;
        Long amount = 1000L;
        String proposal = "Test Proposal";

        Projects project = new Projects();
        project.setId(projectId);
        project.setStatus(Projects.Status.OPEN);

        User user = new User();
        user.setId(freelancerId);
        user.setRole(User.Role.FREELANCER);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(userRepo.findById(freelancerId)).thenReturn(Optional.of(user));

        Bid bid = bidService.createBids(projectId, freelancerId, amount, proposal);

        verify(bidRepo, times(1)).save(any(Bid.class));
        assertEquals(projectId, bid.getProjectId());
        assertEquals(freelancerId, bid.getFreelancerId());
        assertEquals(amount, bid.getAmount());
        assertEquals(proposal, bid.getProposal());
        assertEquals(Bid.BidStatus.PENDING, bid.getStatus());
    }
}