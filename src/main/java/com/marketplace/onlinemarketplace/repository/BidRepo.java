package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);
    /**
     * Delete all bids with bidDate before the given date.
     * @param date cutoff date; bids older than this will be deleted
     * @return number of bids deleted
     */
    long deleteByBidDateBefore(LocalDateTime date);


}
