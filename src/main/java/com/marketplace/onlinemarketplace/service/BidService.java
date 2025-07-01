import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BidService {
    private List<Bid> bids;

    public void deleteBidsByDate(Date date) {
        Iterator<Bid> iterator = bids.iterator();
        while (iterator.hasNext()) {
            Bid bid = iterator.next();
            if (bid.getDate().equals(date)) {
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

    public void setDate(Date date) {
        this.date = date;
    }
}
