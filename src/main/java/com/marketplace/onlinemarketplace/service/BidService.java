package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.model.Bid;
import com.marketplace.onlinemarketplace.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    public List<Bid> getAllBidsById(Long id) {
        List<Bid> bids = bidRepository.findById(id);
        if (bids.isEmpty()) {
            // Handle the case where no bids are found
            // This could be logging, throwing an exception, or returning an empty list
            return List.of();
        }
        return bids;
    }
}
