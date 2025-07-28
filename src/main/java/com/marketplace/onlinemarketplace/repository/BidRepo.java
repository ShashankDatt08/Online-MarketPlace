package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    void deleteByBidDateBefore(LocalDateTime date);

    @Modifying
    @Transactional
    @Query("update Bid b set b.status = :status where b.bidDate < :date")
    int updateStatusByBidDateBefore(@Param("status") Bid.BidStatus status, @Param("date") LocalDateTime date);


}
