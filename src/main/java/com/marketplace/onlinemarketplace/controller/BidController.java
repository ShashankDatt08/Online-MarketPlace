package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.service.BidService;
import com.marketplace.onlinemarketplace.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/bid")
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/add/{projectId}")
    public ResponseEntity addBid(@PathVariable Long projectId, @RequestBody Bid bidRequest) {
        try {

            Bid bid = bidService.createBids(projectId, bidRequest.getFreelancerId(), bidRequest.getAmount(), bidRequest.getProposal());
            return ResponseEntity.ok(bid);

        } catch (Exception e) {
            throw new RuntimeException("unable to add bid Please check the Freelancer Id");
        }

    }

    @GetMapping("/{projectId}/{clientId}")
    public ResponseEntity getBid(@PathVariable Long projectId , Long clientId)  {
        try {
            boolean isClientViewing = projectService.isClientOwner(projectId, clientId);
            List<Bid> bid = bidService.getBidById(projectId, isClientViewing);
            return ResponseEntity.ok(bid);
        } catch (Exception e) {
            throw new RuntimeException("Please Enter Correct Bid Id");
        }
    }

    @PutMapping("/accept/{bidId}/{clientId}")
    public ResponseEntity acceptBid(@PathVariable Long bidId, @PathVariable Long clientId) {
            Bid bid = bidService.acceptBid(bidId, clientId);
            return ResponseEntity.ok(bid);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Bid>> getAllBids() {
        try{
            List<Bid> bid = bidService.getAllBids();
            return ResponseEntity.ok(bid);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete all bids placed before the specified date/time.
     * @param date the threshold date/time (ISO format)
     */
    @DeleteMapping("/deleteByDate/{date}")
    public ResponseEntity<String> deleteBidsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        try {
            bidService.deleteBidsByDate(date);
            return ResponseEntity.ok("Bids deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error deleting bids: " + e.getMessage());
        }
    }
}
