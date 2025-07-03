package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.BidRepository;
import entities.Bid;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BidService {

    private static final Logger logger = LoggerFactory.getLogger(BidService.class);

    @Autowired
    private BidRepository bidRepository;

    public List<Bid> getAllBidsById(Long id) {
        try {
            Optional<List<Bid>> bids = bidRepository.findAllById(id);
            if (bids.isPresent()) {
                return bids.get();
            } else {
                logger.warn("No bids found for ID: " + id);
                return List.of();
            }
        } catch (Exception e) {
            logger.error("Error retrieving bids for ID: " + id, e);
            throw e;
        }
    }
}