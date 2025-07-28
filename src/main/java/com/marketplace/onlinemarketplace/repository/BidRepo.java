package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    /**
     * Remove all bid records with bidDate before the given cutoff.
     *
     * @param date cutoff LocalDateTime; bids earlier than this will be deleted
     */
    void deleteByBidDateBefore(LocalDateTime date);


}
