package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.service.BidService;
import com.marketplace.onlinemarketplace.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/bid")
@Tag(name = "Bid", description = "Bid management APIs")
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
        try {
            List<Bid> bid = bidService.getAllBids();
            return ResponseEntity.ok(bid);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Operation(summary = "Delete bids before a specified date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bids deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error deleting bids", content = @Content)
    })
    @DeleteMapping("/delete/{date}")
    public ResponseEntity<String> deleteBidsBeforeDate(@PathVariable String date) {
        try {
            LocalDateTime cutoff = LocalDateTime.parse(date);
            bidService.deleteBidsBefore(cutoff);
            return ResponseEntity.ok("Bids deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting bids: " + e.getMessage());
        }
    }
}
