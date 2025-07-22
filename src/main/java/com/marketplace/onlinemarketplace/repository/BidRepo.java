package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDateTime;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    void deleteByBidDateBefore(LocalDateTime date);

    /**
     * Update bid status for all bids before the given date.
     * @param status new status to set
     * @param date cutoff date; bids before this will be updated
     * @return number of bids updated
     */
    @Modifying
    @Query("UPDATE Bid b SET b.status = :status WHERE b.bidDate < :date")
    int updateStatusByBidDateBefore(@Param("status") Bid.BidStatus status,
                                    @Param("date") LocalDateTime date);


}
