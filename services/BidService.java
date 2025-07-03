package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.BidRepository;
import entities.Bid;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    private static final Logger logger = Logger.getLogger(BidService.class.getName());

    public List<Bid> getAllBidsById(Long id) {
        try {
            return bidRepository.findAllById(id);
        } catch (Exception e) {
            logger.severe("Error retrieving bids for ID: " + id + ", " + e.getMessage());
            throw e;
        }
    }
}
