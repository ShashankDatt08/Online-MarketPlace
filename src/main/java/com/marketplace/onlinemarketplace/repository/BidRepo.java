package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    void deleteByBidDateBefore(LocalDateTime date);

    /**
     * Finds all bids placed before the given date.
     *
     * @param date the cutoff date (exclusive)
     * @return list of bids with bidDate before the specified date
     */
    List<Bid> findByBidDateBefore(LocalDateTime date);


}
