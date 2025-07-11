package com.marketplace.onlinemarketplace.repository;

import com.marketplace.onlinemarketplace.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface BidRepo extends JpaRepository<Bid, Long> {
    List<Bid> findByProjectId(Long projectId);
    List<Bid> findByProjectIdAndStatus(Long projectId, Bid.BidStatus status);

    @Modifying
    @Transactional
    void deleteByBidDateBefore(LocalDateTime date);


}
