package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.ClientProfile;
import com.marketplace.onlinemarketplace.entity.ClientRequest;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.service.ClientProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/client")
@Tag(name = "Client Profile", description = "Client Profile management APIs")
public class ClientProfileController {

    @Autowired
    private ClientProfileService clientProfileService;

    @Operation(summary = "Create a new client profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",  description = "Client Profile Created Successfully"
            , content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400" , description = "Error Creating Client Profile"
            , content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<String> createClientProfile(@RequestBody ClientRequest profileRequest) {
        try {
            ClientProfile profile = clientProfileService.createProfile(profileRequest);
            return ResponseEntity.ok("Client profile created successfully with ID: " + profile.getId());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error creating client profile: " + e.getMessage());
        }
    }

    @Operation(summary = "Update Client Profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Client Profile Updated Successfully"
            , content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400" , description = "Error Updating Profile",
            content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<String> updateClientProfile(@RequestBody ClientRequest profileRequest) {
        try {
            ClientProfile updatedProfile = clientProfileService.updateProfile(profileRequest);
            return ResponseEntity.ok("Client profile updated successfully with ID: " + updatedProfile.getId());
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error updating profile: " + e.getMessage());
        }
    }

    @Operation(summary = "Get a client profile by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client profile fetched successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error fetching profile",
                    content = @Content)
    })
    @GetMapping("/{userId}")
    public ResponseEntity getProfile(@PathVariable Long userId) {

        ClientProfile profile = clientProfileService.getClientProfileById(userId)
                .orElseThrow(() -> new RuntimeException("Enter Correct Id"));

        if (profile.getUser().getRole() != User.Role.CLIENT) {
            throw new RuntimeException("User is not a Client and cannot fetch a Client details.");
        }
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Delete a client profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client profile deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error deleting profile",
                    content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClientProfile(@PathVariable Long id) {
        try {
            clientProfileService.deleteProfile(id);
            return ResponseEntity.ok("Client profile deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error deleting profile: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all client profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all client profiles",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error fetching client profiles",
                    content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<List<ClientProfile>> getClientProfile() {
        try {
            List<ClientProfile> clientProfiles =  clientProfileService.getAllClientProfile();
            return ResponseEntity.ok(clientProfiles);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}

