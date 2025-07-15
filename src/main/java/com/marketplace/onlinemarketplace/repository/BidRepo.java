package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    /**
     * Delete all bids placed before the given date.
     */
    void deleteByBidDateBefore(LocalDateTime date);

    /**
     * Find all bids submitted by the given freelancer.
     *
     * @param freelancerId the freelancer's user ID
     * @return list of bids by the freelancer
     */
    List<Bid> findByFreelancerId(Long freelancerId);


}
