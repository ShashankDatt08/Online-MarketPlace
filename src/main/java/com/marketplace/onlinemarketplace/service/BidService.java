package com.marketplace.onlinemarketplace.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BidService {
    private List<Bid> bids;

    // Existing methods...

    /**
     * Deletes all bids that were created before the specified date.
     *
     * @param date the date before which all bids should be deleted
     */
    public void deleteBidsBeforeDate(Date date) {
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (bid.getCreationDate().before(date)) {
                iterator.remove();
            }
        }
    }
}

class Bid {
    private Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }
}