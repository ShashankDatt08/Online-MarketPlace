package com.marketplace.service;

import java.util.Date;
import java.util.List;
import com.marketplace.model.Bid;
import com.marketplace.repository.BidRepository;

public class BidService {
    private BidRepository bidRepository;

    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    // Existing methods for creating, updating, and retrieving bids

    /**
     * Deletes all bids older than the specified date.
     *
     * @param date the date to compare bids against
     */
    public void deleteBidsOlderThan(Date date) {
        List<Bid> bids = bidRepository.findAll();
        for (Bid bid : bids) {
            if (bid.getDate().before(date)) {
                bidRepository.delete(bid);
            }
        }
    }
}