```java
import java.util.List;
import org.springframework.dao.DataAccessException;

public List<Bid> getAllBids() {
    try {
        return bidRepo.findAll();
    } catch (DataAccessException e) {
        throw new RuntimeException("Error occurred while fetching all bids", e);
    }
}
```