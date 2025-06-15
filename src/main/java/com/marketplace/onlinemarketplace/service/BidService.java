```java
import org.springframework.transaction.annotation.Transactional;

@Service
public class BidService {

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    // Existing methods...

    @Transactional(readOnly = true)
    public List<Bid> getAllBids() {
        return bidRepo.findAll();
    }
}
```