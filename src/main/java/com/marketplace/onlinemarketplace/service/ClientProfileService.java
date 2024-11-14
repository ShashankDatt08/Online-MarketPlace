package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.entity.ClientProfile;
import com.marketplace.onlinemarketplace.entity.ClientRequest;
import com.marketplace.onlinemarketplace.entity.FreelancerProfile;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.ClientProfileRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientProfileService {

    @Autowired
    private ClientProfileRepo clientProfileRepo;

    @Autowired
    private UserRepo userRepo;

    public ClientProfile createProfile(ClientRequest clientRequest) {
        User user = userRepo.findById(clientRequest.getId()).orElseThrow(() -> new RuntimeException("Client not found"));

        if (user.getRole() != User.Role.CLIENT) {
            throw new RuntimeException("User is not a Client and cannot create a ClientN profile.");
        }

        Optional<ClientProfile> existingProfileOptional = clientProfileRepo.findByUser(user);
        ClientProfile clientProfile;

        if (existingProfileOptional.isPresent()) {
            clientProfile = existingProfileOptional.get();
        } else {
            clientProfile = new ClientProfile();
            clientProfile.setUser(user);
            clientProfile.setId(user.getId());
        }

        clientProfile.setUserName(user.getUsername());
        clientProfile.setEmail(clientRequest.getEmail());
        clientProfile.setCompanyName(clientRequest.getCompanyName());
        clientProfile.setLocation(clientRequest.getLocation());
        clientProfile.setIndustry(clientRequest.getIndustry());

        return clientProfileRepo.save(clientProfile);
    }

    public Optional<ClientProfile> getClientProfileById(Long clientId) {
        return clientProfileRepo.findById(clientId);
    }

    public ClientProfile saveClientProfile(ClientProfile clientProfile) {
        return clientProfileRepo.save(clientProfile);
    }

    public ClientProfile updateProfile(ClientRequest profileRequest) {
        User user = userRepo.findById(profileRequest.getId()).orElseThrow(() -> new RuntimeException("Client not found"));

        Optional<ClientProfile> existingProfile = clientProfileRepo.findByUser(user);
        if (existingProfile.isEmpty()) {
            throw new RuntimeException("Client profile not found for the user");
        }

        ClientProfile existingClientProfile = new ClientProfile();
        existingClientProfile.setUser(user);
        existingClientProfile.setUserName(user.getUsername());
        existingClientProfile.setEmail(profileRequest.getEmail());
        existingClientProfile.setCompanyName(profileRequest.getCompanyName());
        existingClientProfile.setLocation(profileRequest.getLocation());
        existingClientProfile.setIndustry(profileRequest.getIndustry());

        return clientProfileRepo.save(existingClientProfile);

    }
    @Transactional
    public void deleteProfile(Long id) {
        ClientProfile profile = clientProfileRepo.findById(id).orElseThrow(() -> new RuntimeException("Client profile not found"));
        try {
            clientProfileRepo.delete(profile);
        } catch (Exception e) {
            System.out.println("Freelancer profile could not be deleted." + e.getMessage());
        }
    }

    public List<ClientProfile> getAllClientProfile() {
       return clientProfileRepo.findAll();
    }
}
