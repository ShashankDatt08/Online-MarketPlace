package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    /**
     * Deletes all bids with a bidDate before the given date.
     * @param date cutoff date; bids before this date will be deleted
     * @return the number of bids deleted
     */
    long deleteByBidDateBefore(LocalDateTime date);


}
