package com.marketplace.onlinemarketplace.controller;


import com.marketplace.onlinemarketplace.entity.Review;
import com.marketplace.onlinemarketplace.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity addReview( @RequestParam Long reviewerId,
                                     @RequestParam String freelancerName,
                                     @RequestParam Long projectId,
                                     @RequestParam int rating,
                                     @RequestParam(required = false) String comment) {

        try{
            Review review = reviewService.addReview(reviewerId,freelancerName,projectId,rating,comment);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



}
