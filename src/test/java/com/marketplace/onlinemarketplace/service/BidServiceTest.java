import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BidServiceTest {

    @InjectMocks
    private BidService bidService;

    @Mock
    private BidRepository bidRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateBid_Success() {
        Bid bid = new Bid();
        when(bidRepository.save(any(Bid.class))).thenReturn(bid);

        Bid result = bidService.createBid(bid);

        assertNotNull(result);
        verify(bidRepository, times(1)).save(bid);
    }

    @Test
    public void testCreateBid_Failure() {
        when(bidRepository.save(any(Bid.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> bidService.createBid(new Bid()));
        verify(bidRepository, times(1)).save(any(Bid.class));
    }

    @Test
    public void testGetBid_Success() {
        Bid bid = new Bid();
        when(bidRepository.findById(anyLong())).thenReturn(Optional.of(bid));

        Bid result = bidService.getBid(1L);

        assertNotNull(result);
        verify(bidRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetBid_NotFound() {
        when(bidRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BidNotFoundException.class, () -> bidService.getBid(1L));
        verify(bidRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateBid_Success() {
        Bid bid = new Bid();
        when(bidRepository.findById(anyLong())).thenReturn(Optional.of(bid));
        when(bidRepository.save(any(Bid.class))).thenReturn(bid);

        Bid result = bidService.updateBid(1L, bid);

        assertNotNull(result);
        verify(bidRepository, times(1)).findById(1L);
        verify(bidRepository, times(1)).save(bid);
    }

    @Test
    public void testUpdateBid_NotFound() {
        when(bidRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BidNotFoundException.class, () -> bidService.updateBid(1L, new Bid()));
        verify(bidRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteBid_Success() {
        Bid bid = new Bid();
        when(bidRepository.findById(anyLong())).thenReturn(Optional.of(bid));
        doNothing().when(bidRepository).delete(bid);

        bidService.deleteBid(1L);

        verify(bidRepository, times(1)).findById(1L);
        verify(bidRepository, times(1)).delete(bid);
    }

    @Test
    public void testDeleteBid_NotFound() {
        when(bidRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BidNotFoundException.class, () -> bidService.deleteBid(1L));
        verify(bidRepository, times(1)).findById(1L);
    }
}