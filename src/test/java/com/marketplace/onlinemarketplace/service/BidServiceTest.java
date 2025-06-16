package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.BidRepo;
import com.marketplace.onlinemarketplace.repository.ProjectRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BidServiceTest {

    @InjectMocks
    private BidService bidService;

    @Mock
    private BidRepo bidRepo;

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateBids() {
        Projects project = new Projects();
        project.setStatus(Projects.Status.OPEN);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        User user = new User();
        user.setRole(User.Role.FREELANCER);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        Bid bid = new Bid();
        when(bidRepo.save(any(Bid.class))).thenReturn(bid);

        Bid result = bidService.createBids(1L, 1L, 100L, "proposal");
        assertNotNull(result);
    }

    @Test
    public void testCreateBidsProjectNotFound() {
        when(projectRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bidService.createBids(1L, 1L, 100L, "proposal"));
    }

    @Test
    public void testCreateBidsProjectClosed() {
        Projects project = new Projects();
        project.setStatus(Projects.Status.CLOSED);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));
        assertThrows(RuntimeException.class, () -> bidService.createBids(1L, 1L, 100L, "proposal"));
    }

    @Test
    public void testCreateBidsUserNotFreelancer() {
        Projects project = new Projects();
        project.setStatus(Projects.Status.OPEN);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        User user = new User();
        user.setRole(User.Role.CLIENT);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> bidService.createBids(1L, 1L, 100L, "proposal"));
    }

    @Test
    public void testGetBidById() {
        Bid bid = new Bid();
        bid.setFreelancerId(1L);
        bid.setFreelancerName("Freelancer");
        when(bidRepo.findByProjectId(anyLong())).thenReturn(Arrays.asList(bid));

        assertNotNull(bidService.getBidById(1L, false));
    }

    @Test
    public void testAcceptBid() {
        Bid bid = new Bid();
        bid.setProjectId(1L);
        when(bidRepo.findById(anyLong())).thenReturn(Optional.of(bid));

        Projects project = new Projects();
        project.setClientId(1L);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        when(bidRepo.findByProjectIdAndStatus(anyLong(), any(Bid.BidStatus.class))).thenReturn(Arrays.asList());

        assertNotNull(bidService.acceptBid(1L, 1L));
    }

    @Test
    public void testAcceptBidNotFound() {
        when(bidRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bidService.acceptBid(1L, 1L));
    }

    @Test
    public void testAcceptBidProjectNotFound() {
        Bid bid = new Bid();
        bid.setProjectId(1L);
        when(bidRepo.findById(anyLong())).thenReturn(Optional.of(bid));

        when(projectRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bidService.acceptBid(1L, 1L));
    }

    @Test
    public void testAcceptBidNotClient() {
        Bid bid = new Bid();
        bid.setProjectId(1L);
        when(bidRepo.findById(anyLong())).thenReturn(Optional.of(bid));

        Projects project = new Projects();
        project.setClientId(2L);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        assertThrows(RuntimeException.class, () -> bidService.acceptBid(1L, 1L));
    }

    @Test
    public void testAcceptBidAlreadyAccepted() {
        Bid bid = new Bid();
        bid.setProjectId(1L);
        when(bidRepo.findById(anyLong())).thenReturn(Optional.of(bid));

        Projects project = new Projects();
        project.setClientId(1L);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        when(bidRepo.findByProjectIdAndStatus(anyLong(), any(Bid.BidStatus.class))).thenReturn(Arrays.asList(new Bid()));
        assertThrows(RuntimeException.class, () -> bidService.acceptBid(1L, 1L));
    }
}