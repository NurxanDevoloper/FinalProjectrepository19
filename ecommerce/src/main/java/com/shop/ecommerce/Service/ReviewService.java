package com.shop.ecommerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shop.ecommerce.Entity.Review;
import com.shop.ecommerce.Entity.User;
import com.shop.ecommerce.Repository.ProductsRepository;
import com.shop.ecommerce.Repository.ReviewRepository;
import com.shop.ecommerce.Repository.UserRepository;
import com.shop.ecommerce.dto.ReviewDto;



@Service
public class ReviewService {
	  @Autowired
	    private ReviewRepository reviewRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private ProductsRepository productsRepository;

	    public Review create(ReviewDto reviewDto) {
	        User user = getCurrentUser();

	        //  проверяем, существует ли продукт
	        productsRepository.findById(reviewDto.getProductsId())
	                .orElseThrow(() -> new RuntimeException("products not found"));

	        Review review = new Review();
	        review.setProductsId(reviewDto.getProductsId());
	        review.setComment(reviewDto.getComment());
	        review.setRating(reviewDto.getRating());
	        review.setUserId(user.getId());

	        return reviewRepository.save(review);
	    }

	    public List<Review> getByProducts(Long productsId) {
	        return reviewRepository.findByProductsId(productsId);
	    }

	    public void deleteReview(Long id) {
	        Review review = reviewRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("review not found"));

	        User user = getCurrentUser();

	        if (!review.getUserId().equals(user.getId())) {
	            throw new RuntimeException("You are not allowed to delete this review");
	        }

	        reviewRepository.deleteById(id);
	    }

	    private User getCurrentUser() {
	        String username = SecurityContextHolder
	                .getContext()
	                .getAuthentication()
	                .getName();

	        return userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("user not found"));
	    }
}
