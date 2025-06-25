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
        // existing code
    }

    public Optional<ClientProfile> getClientProfileById(Long clientId) {
        // existing code
    }

    public ClientProfile saveClientProfile(ClientProfile clientProfile) {
        // existing code
    }

    public ClientProfile updateProfile(ClientRequest profileRequest) {
        // existing code
    }

    public List<ClientProfile> getAllClientProfile() {
        // existing code
    }

    public void deleteProfile(Long clientId) {
        Optional<ClientProfile> clientProfile = clientProfileRepo.findById(clientId);
        if (clientProfile.isPresent()) {
            clientProfileRepo.delete(clientProfile.get());
        } else {
            throw new RuntimeException("Client profile not found");
        }
    }
}