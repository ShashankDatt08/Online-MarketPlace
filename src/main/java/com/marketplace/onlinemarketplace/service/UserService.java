package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.repository.BidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private BidRepo bidRepo;

    public void deleteAllBids() {
        bidRepo.deleteAll();
    }

    public void deleteBidById(Long id) {
        Optional<Bid> bid = bidRepo.findById(id);
        if (bid.isPresent()) {
            bidRepo.deleteById(id);
        } else {
            throw new RuntimeException("Bid not found with id: " + id);
        }
    }
}