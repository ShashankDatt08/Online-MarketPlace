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
    public void testCreateBids_Success() {
        Projects project = new Projects();
        project.setStatus(Projects.Status.OPEN);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        User user = new User();
        user.setRole(User.Role.FREELANCER);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        Bid bid = new Bid();
        when(bidRepo.save(any(Bid.class))).thenReturn(bid);

        Bid result = bidService.createBids(1L, 1L, 1000L, "proposal");
        assertNotNull(result);
    }

    @Test
    public void testCreateBids_ProjectNotFound() {
        when(projectRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bidService.createBids(1L, 1L, 1000L, "proposal"));
    }

    @Test
    public void testCreateBids_ProjectNotAvailable() {
        Projects project = new Projects();
        project.setStatus(Projects.Status.CLOSED);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        assertThrows(RuntimeException.class, () -> bidService.createBids(1L, 1L, 1000L, "proposal"));
    }

    @Test
    public void testCreateBids_UserNotFreelancer() {
        Projects project = new Projects();
        project.setStatus(Projects.Status.OPEN);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        User user = new User();
        user.setRole(User.Role.CLIENT);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> bidService.createBids(1L, 1L, 1000L, "proposal"));
    }
}