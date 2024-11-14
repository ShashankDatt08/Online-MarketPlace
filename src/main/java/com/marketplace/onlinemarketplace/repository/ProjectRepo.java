package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Projects , Long> {
}
