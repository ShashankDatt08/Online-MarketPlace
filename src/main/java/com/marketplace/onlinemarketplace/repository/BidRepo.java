package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    /**
     * Delete all bids made before the given date/time.
     *
     * @param date the cutoff date/time; bids before this will be removed
     */
    void deleteByBidDateBefore(LocalDateTime date);
    /**
     * Delete all bids made on the specified date (inclusive of start, exclusive of end).
     *
     * @param start inclusive start of the date period
     * @param end exclusive end of the date period
     */
    void deleteByBidDateBetween(LocalDateTime start, LocalDateTime end);

}
