package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    void deleteByBidDateBefore(LocalDateTime date);

    /**
     * Update status to REJECTED for all pending bids placed before the given date.
     *
     * @param date cutoff date
     * @return number of bids updated
     */
    @Modifying
    @Query("UPDATE Bid b SET b.status = 'REJECTED' WHERE b.bidDate < :date AND b.status = 'PENDING'")
    int updateStatusToRejectedByBidDateBefore(@Param("date") LocalDateTime date);


}
