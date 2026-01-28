package com.shop.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommerce.Entity.Review;
import com.shop.ecommerce.Service.ReviewService;
import com.shop.ecommerce.dto.ReviewDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewController {
	   @Autowired
	    private ReviewService reviewService;

	    @PostMapping
	    public Review create(@Valid @RequestBody ReviewDto reviewDto) {
	        return reviewService.create(reviewDto);
	    }

	    @GetMapping("/products/{productsId}")
	    public List<Review> getByProducts(@PathVariable Long productsId) {
	        return reviewService.getByProducts(productsId);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteReview(@PathVariable Long id) {
	        reviewService.deleteReview(id);
	    }
}
