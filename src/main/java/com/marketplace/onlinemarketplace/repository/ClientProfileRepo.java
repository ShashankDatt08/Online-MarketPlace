package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.ClientProfile;
import com.marketplace.onlinemarketplace.entity.FreelancerProfile;
import com.marketplace.onlinemarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientProfileRepo extends JpaRepository<ClientProfile, Long> {
    ClientProfile findByUser_Username(String userName);
    Optional<ClientProfile> findByUser(User user);

}
