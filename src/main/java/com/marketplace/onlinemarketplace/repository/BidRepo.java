package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    /**
     * Delete all bids with bidDate before the given date.
     * @param date cutoff date; bids before this will be removed
     */
    @Modifying
    @Transactional
    void deleteByBidDateBefore(LocalDateTime date);


}
