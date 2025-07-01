import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BidService {
    private List<Bid> bids;

    public void deleteBidsBeforeOrOnDate(Date date) {
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (!bid.getCreationDate().after(date)) {
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