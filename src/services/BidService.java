import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BidService {

    private static final Logger logger = LoggerFactory.getLogger(BidService.class);

    @Autowired
    private BidRepository bidRepository;

    public List<Bid> getAllBidsById(Long id) {
        try {
            logger.info("Fetching all bids for ID: {}", id);
            return bidRepository.findAllById(id);
        } catch (Exception e) {
            logger.error("Error fetching bids for ID: {}", id, e);
            throw new RuntimeException("Failed to fetch bids", e);
        }
    }
}
