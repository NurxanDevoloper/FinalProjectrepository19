package com.shop.ecommerce.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "review")
@Data
public class Review {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Comment cannot be empty")
	    @Size(min = 3, max = 500, message = "Comment must be between 3 and 500 characters")
	    private String comment;

	    @NotNull(message = "User ID is required")
	    private Long userId;

	    @NotNull(message = "Products ID is required")
	    private Long productsId;

	    @NotNull(message = "Rating is required")
	    @Min(value = 1, message = "Rating must be at least 1")
	    @Max(value = 5, message = "Rating must be at most 5")
	    private Integer rating;
}
