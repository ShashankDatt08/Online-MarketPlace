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
     * Find all bids before the given date with the specified status.
     *
     * @param date cutoff date
     * @param status bid status to filter
     * @return list of matching bids
     */
    List<Bid> findByBidDateBeforeAndStatus(LocalDateTime date, Bid.BidStatus status);


}
