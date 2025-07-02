package com.marketplace.onlinemarketplace.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marketplace.onlinemarketplace.model.Bid;
import com.marketplace.onlinemarketplace.repository.BidRepository;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    // Other existing methods

    /**
     * Deletes all bids created on or before the specified date.
     *
     * @param date the date to compare bids against
     */
    public void deleteBidsByDate(Date date) {
        List<Bid> bidsToDelete = bidRepository.findAllByCreationDateBeforeOrEqual(date);
        bidRepository.deleteAll(bidsToDelete);
    }
}
