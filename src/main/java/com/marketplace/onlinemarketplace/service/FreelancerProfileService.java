package com.marketplace.onlinemarketplace.service;


import com.marketplace.onlinemarketplace.entity.FreelancerProfile;
import com.marketplace.onlinemarketplace.entity.FreelancerRequest;
import com.marketplace.onlinemarketplace.entity.Review;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.FreelancerProfileRepo;
import com.marketplace.onlinemarketplace.repository.ReviewRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class FreelancerProfileService {

    @Autowired
    private FreelancerProfileRepo freelancerProfileRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SystemMetricsAutoConfiguration systemMetricsAutoConfiguration;

    @Autowired
    private ReviewRepo reviewRepo;

    public FreelancerProfile createProfile(FreelancerRequest request) {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != User.Role.FREELANCER) {
            throw new RuntimeException("User is not a freelancer and cannot create a freelancer profile.");
        }

        Optional<FreelancerProfile> existingProfile = freelancerProfileRepo.findByUser(user);
        FreelancerProfile freelancer;

        if (existingProfile.isPresent()) {
            freelancer = existingProfile.get();
        } else {
            freelancer = new FreelancerProfile();
            freelancer.setUser(user);
        }

        FreelancerProfile profile = new FreelancerProfile();
        profile.setUser(user);
        profile.setId(user.getId());
        profile.setUsername(user.getUsername());
        profile.setPortfolio(request.getPortfolio());
        profile.setSkills(request.getSkills());
        profile.setBio(request.getBio());
        profile.setRating(request.getRating());
        profile.setCompletedProjectsCount(request.getCompletedProjectsCount());

        return freelancerProfileRepo.save(profile);
    }

    public Optional<FreelancerProfile> getFreelancerProfileById(Long id) {
        return freelancerProfileRepo.findById(id);
    }


    public FreelancerProfile updateProfile(FreelancerRequest profileRequest) {

        User user = userRepo.findById(profileRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<FreelancerProfile> existingProfile = freelancerProfileRepo.findByUser(user);
        if (existingProfile.isEmpty()) {
            throw new RuntimeException("Freelancer profile not found for the user");
        }
        FreelancerProfile existingFreelancerProfile = new FreelancerProfile();
        existingFreelancerProfile.setUser(user);
        existingFreelancerProfile.setId(user.getId());
        existingFreelancerProfile.setUsername(user.getUsername());
        existingFreelancerProfile.setPortfolio(profileRequest.getPortfolio());
        existingFreelancerProfile.setSkills(profileRequest.getSkills());
        existingFreelancerProfile.setBio(profileRequest.getBio());
        existingFreelancerProfile.setRating(profileRequest.getRating());
        existingFreelancerProfile.setCompletedProjectsCount(profileRequest.getCompletedProjectsCount());

        return freelancerProfileRepo.save(existingFreelancerProfile);


    }

   @Transactional
    public void deleteProfile(Long id) {
        FreelancerProfile profile = freelancerProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("Freelancer profile not found"));
        try {
            freelancerProfileRepo.delete(profile);
        } catch (Exception e) {
            System.out.println("Freelancer profile could not be deleted." + e.getMessage());
        }
    }

    public List<FreelancerProfile> getAllClientProfile() {
        return freelancerProfileRepo.findAll();
    }

    public List<Review> getReviewbyFreelancerName(String freelancerName) {

        List<Review> review = reviewRepo.findByFreelancerName(freelancerName);

        if(review.isEmpty()) {
            throw new RuntimeException("No Review found for " + freelancerName);
        }

        return review;

    }
}
