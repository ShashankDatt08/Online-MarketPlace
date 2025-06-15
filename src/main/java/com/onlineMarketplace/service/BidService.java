```java
import java.util.Collections;

public List<Bid> getAllBids() {
    try {
        return bidRepo.findAll();
    } catch (Exception e) {
        return Collections.emptyList();
    }
}
```