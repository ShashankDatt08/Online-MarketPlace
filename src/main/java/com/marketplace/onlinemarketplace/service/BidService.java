import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

class Bid {
    private Date creationDate;

    public Bid(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}

class BidService {
    private List<Bid> bids;

    public BidService() {
        this.bids = new ArrayList<>();
    }

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public void deleteBidsBeforeOrOnDate(Date date) {
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (!bid.getCreationDate().after(date)) {
                iterator.remove();
            }
        }
    }

    public void deleteBidsOlderThan(Date date) {
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (bid.getCreationDate().before(date)) {
                iterator.remove();
            }
        }
    }

    public void deleteBidsOnDate(Date date) {
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (bid.getCreationDate().equals(date)) {
                iterator.remove();
            }
        }
    }

    // Deletes all bids older than the specified date
    public void delete(Date date) {
        if (date == null) {
            return;
        }
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (bid.getCreationDate().before(date)) {
                iterator.remove();
            }
        }
    }

    public List<Bid> getBids() {
        return bids;
    }
}