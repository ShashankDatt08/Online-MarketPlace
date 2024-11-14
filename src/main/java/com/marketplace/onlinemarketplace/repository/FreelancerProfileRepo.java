package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.FreelancerProfile;
import com.marketplace.onlinemarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerProfileRepo extends JpaRepository<FreelancerProfile, Long> {
    FreelancerProfile findByUser_Username(String username);
    Optional<FreelancerProfile> findByUser(User user);
}