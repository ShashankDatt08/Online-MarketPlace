package com.marketplace.onlinemarketplace.service;


import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.entity.Review;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.ProjectRepo;
import com.marketplace.onlinemarketplace.repository.ReviewRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ProjectRepo projectRepo;

    public Review addReview(Long reviewerId, String freelancerName, Long projectId, int rating, String comment) {
        User user = userRepo.findByUsername(freelancerName).orElseThrow(() -> new RuntimeException("Username not found"));

        if (user.getRole() != User.Role.FREELANCER) {
            throw new RuntimeException(freelancerName + " is not a freelancer");
        }

        Projects project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        if(!project.getClientId().equals(reviewerId)) {
            throw new RuntimeException("Only the client who created the project can leave a review");

        }

        Review review = new Review();
        review.setReviewerId(reviewerId);
        review.setFreelancerName(freelancerName);
        review.setProjectId(projectId);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewerName(project.getClientName());
        review.setTimestamp(LocalDateTime.now());

        return reviewRepo.save(review);
    }
}
