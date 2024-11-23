package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Projects , Long> {
    @Query("SELECT p FROM Projects p WHERE p.ClientId = :clientId")
    List<Projects> findByClientId(@Param("clientId") Long clientId);

    @Query("SELECT p FROM Projects p JOIN Bid b ON p.id = b.projectId WHERE b.freelancerId = :freelancerId AND b.status = 'ACCEPTED'")
    List<Projects> findByFreelancerId(@Param("freelancerId") Long freelancerId);
}
