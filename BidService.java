import java.util.HashMap;
import java.util.Map;

public class BidService {
    private Map<Integer, Bid> bids = new HashMap<>();

    public void addBid(int id, Bid bid) {
        bids.put(id, bid);
    }

    public Bid getBid(int id) {
        return bids.get(id);
    }

    public boolean deleteBid(int id) {
        if (bids.containsKey(id)) {
            bids.remove(id);
            return true;
        }
        return false;
    }
}

class Bid {
    private int id;
    private double amount;

    public Bid(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
}
