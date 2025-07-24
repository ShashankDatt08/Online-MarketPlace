package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.entity.ProjectRequest;
import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.BidRepo;
import com.marketplace.onlinemarketplace.repository.ProjectRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BidRepo bidRepo;

    public Projects createProject(ProjectRequest projectReq) {
        User user = userRepo.findById(projectReq.getId()).orElseThrow(() -> new RuntimeException("Client Id not Found"));

        if (user.getRole() != User.Role.CLIENT) {
            throw new RuntimeException("Only Clients can add Projects");
        }

        Projects project = new Projects();
        System.out.println("Client ID: " + Long.valueOf(user.getId()));
        project.setClientId(projectReq.getId());
        project.setClientName(user.getUsername());
        project.setClientEmail(user.getEmail());
        project.setTitle(projectReq.getTitle());
        project.setProjectDescription(projectReq.getProjectDescription());
        project.setStatus(Projects.Status.valueOf(projectReq.getStatus().toString()));
        project.setBudget(projectReq.getBudget());
        project.setCreatedAt(LocalDateTime.now());
        System.out.println("Project Data: " + project);
        return projectRepo.save(project);

    }

    public List<Projects> getAllClientProfile() {
        return projectRepo.findAll();
    }


    public Projects updateProject(ProjectRequest projectReq) {
        Projects existingProject = projectRepo.findById(projectReq.getId()).orElseThrow(() -> new RuntimeException("Client Id not Found"));

        Optional<User> user = userRepo.findById(existingProject.getClientId());
        if (user.get().getRole() != User.Role.CLIENT) {
            throw new RuntimeException("Only Clients can update Projects");
        }

        if (projectReq.getTitle() != null) {
            existingProject.setTitle(projectReq.getTitle());
        }

        if (projectReq.getProjectDescription() != null) {
            existingProject.setProjectDescription(projectReq.getProjectDescription());
        }

        if (projectReq.getBudget() != null) {
            existingProject.setBudget(projectReq.getBudget());
        }

        if (projectReq.getStatus() != null) {
            existingProject.setStatus(projectReq.getStatus());
        }

        existingProject.setUpdatedAt(LocalDateTime.now());

        return projectRepo.save(existingProject);


    }

    public Projects getProjectById(Long id) {
        return projectRepo.getReferenceById(id);
    }

    public void deleteProject(Long id, Long clientId) {
        Projects project = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project Id not Found"));

        Optional<User> user = userRepo.findById(clientId);
        if (user.get().getRole() != User.Role.CLIENT) {
            throw new RuntimeException("Only Clients can delete Projects");
        }
        projectRepo.delete(project);
    }

    public List<Bid> getBidforProject(Long projectId) {
        return bidRepo.findByProjectId(projectId);
    }

    public boolean isClientOwner(Long projectId, Long clientId) {
        Projects project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        return project.getClientId().equals(clientId);
    }

    public Bid acceptBid(Long bidId, Long clientId) {
        Bid bid = bidRepo.findById(bidId).orElseThrow(() -> new RuntimeException("Bid not found"));
        Projects projects = projectRepo.findById(clientId).orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projects.getClientId().equals(clientId)) {
            throw new RuntimeException("Client Id not Found");
        }

        bid.setStatus(Bid.BidStatus.ACCEPTED);
        return bidRepo.save(bid);
    }

    /**
     * Deletes all projects created before the specified date.
     *
     * @param date the cutoff creation date
     */
    public void deleteProjectsBefore(LocalDateTime date) {
        projectRepo.deleteByCreatedAtBefore(date);
    }
}
