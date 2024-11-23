package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {

    List<Review> findByFreelancerName(String freelancerName);
}
