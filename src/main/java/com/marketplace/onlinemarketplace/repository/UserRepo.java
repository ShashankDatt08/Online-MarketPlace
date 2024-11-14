package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
