package com.marketplace.onlinemarketplace.service;

import java.util.Date;
import java.util.List;

public class BidService {

    // Existing methods...

    /**
     * Deletes bids that are older than the specified date.
     *
     * @param date the date to compare bids against
     */
    public void deleteBidsOlderThan(Date date) {
        // Logic to delete bids older than the specified date
        // This could involve calling a repository method to delete
        // bids from the database where the bid date is less than the specified date.
    }
}
