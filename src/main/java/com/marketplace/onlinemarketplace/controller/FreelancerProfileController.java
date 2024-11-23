package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.*;
import com.marketplace.onlinemarketplace.service.FreelancerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/freelancer")
@Tag(name = "Freelancer Profile", description = "Freelancer Profile management APIs")
public class FreelancerProfileController {

    @Autowired
    private FreelancerProfileService freelancerProfileService;


    @Operation(summary = "Create a new freelancer profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer profile created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error creating freelancer profile",
                    content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<String> createFreelancerProfile(@RequestBody FreelancerRequest profileRequest) {
        FreelancerProfile profile = freelancerProfileService.createProfile(profileRequest);
        return ResponseEntity.ok("Freelancer profile created successfully with ID: " + profile.getId());
    }

    @Operation(summary = "Get a freelancer profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer profile fetched successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "User is not a Freelancer",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity getFreelancerProfile(@PathVariable Long id) {

        FreelancerProfile profile = freelancerProfileService.getFreelancerProfileById(id)
                .orElseThrow(() -> new RuntimeException("Enter Correct Id"));

        if(profile.getUser().getRole() != User.Role.FREELANCER){
            return ResponseEntity.status(500).body("User is not A Freelancer");
        }

        return ResponseEntity.ok(profile);

    }

    @Operation(summary = "Update an existing freelancer profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer profile updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error updating profile",
                    content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateFreelancerProfile(@RequestBody FreelancerRequest profileRequest) {
        try {
            FreelancerProfile updatedProfile = freelancerProfileService.updateProfile(profileRequest);
            return ResponseEntity.ok("Freelancer profile updated successfully with ID: " + updatedProfile.getId());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error updating profile: " + e.getMessage());
        }
    }


    @Operation(summary = "Delete a freelancer profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer profile deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error deleting profile",
                    content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFreelancerProfile(@PathVariable Long id) {
        try {
            freelancerProfileService.deleteProfile(id);
            return ResponseEntity.ok("Freelancer profile deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error deleting profile: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FreelancerProfile>> getFreelancerProfile() {
        try {
            List<FreelancerProfile> freelancerProfiles =  freelancerProfileService.getAllClientProfile();
            return ResponseEntity.ok(freelancerProfiles);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/review")
    public ResponseEntity getReview(@RequestParam String freelancerName) {
        try{
            List<Review> review = freelancerProfileService.getReviewbyFreelancerName(freelancerName);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
