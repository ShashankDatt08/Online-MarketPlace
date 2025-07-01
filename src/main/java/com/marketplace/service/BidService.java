package com.marketplace.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BidService {
    private List<Bid> bids;

    // Existing methods...

    /**
     * Deletes all bids older than the specified date.
     *
     * @param date the date to compare bids against
     */
    public void deleteBidsOlderThan(Date date) {
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (bid.getDate().before(date)) {
                iterator.remove();
            }
        }
    }
}

class Bid {
    private Date date;

    public Date getDate() {
        return date;
    }

    // Other fields and methods...
}