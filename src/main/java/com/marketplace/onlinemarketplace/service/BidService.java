package com.marketplace.onlinemarketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marketplace.onlinemarketplace.repository.BidRepository;
import com.marketplace.onlinemarketplace.model.Bid;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    private static final Logger logger = LoggerFactory.getLogger(BidService.class);

    // Existing methods...

    /**
     * Deletes all bids created on or before the specified date.
     *
     * @param date the date to compare bids against
     */
    public void deleteBidsByDate(Date date) {
        try {
            List<Bid> bidsToDelete = bidRepository.findAllByCreationDateBeforeOrEqual(date);
            bidRepository.deleteAll(bidsToDelete);
            logger.info("Deleted {} bids created on or before {}", bidsToDelete.size(), date);
        } catch (Exception e) {
            logger.error("Error occurred while deleting bids by date: ", e);
            throw e;
        }
    }
}