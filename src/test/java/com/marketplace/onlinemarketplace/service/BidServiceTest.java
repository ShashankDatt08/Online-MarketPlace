package com.marketplace.onlinemarketplace.service;

import com.marketplace.onlinemarketplace.repository.BidRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link BidService}.
 */
@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @Mock
    private BidRepo bidRepo;

    @InjectMocks
    private BidService bidService;

    @Test
    void deleteBidsBefore_delegatesToRepository() {
        LocalDateTime cutoff = LocalDateTime.of(2025, 1, 1, 0, 0);
        bidService.deleteBidsBefore(cutoff);
        verify(bidRepo).deleteByBidDateBefore(cutoff);
    }
}
