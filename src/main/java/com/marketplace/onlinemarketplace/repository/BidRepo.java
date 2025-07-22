package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    /**
     * Retrieves all bids placed by a specific freelancer.
     *
     * @param freelancerId the identifier of the freelancer
     * @return list of bids made by the freelancer
     */
    List<Bid> findByFreelancerId(Long freelancerId);

    void deleteByBidDateBefore(LocalDateTime date);


}
