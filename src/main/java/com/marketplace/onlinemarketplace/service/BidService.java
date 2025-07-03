package com.marketplace.onlinemarketplace.service;

import java.util.Date;
import java.util.List;

public class BidService {
    // Existing methods...

    /**
     * Deletes bids that were created before the specified date.
     *
     * @param date the date before which all bids should be deleted
     */
    public void deleteBidsBeforeDate(Date date) {
        // Logic to delete bids from the database
        // Example: bidRepository.deleteByDateBefore(date);
    }
}