package com.shop.ecommerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewDto {
    @NotBlank(message = "Comment cannot be empty")
    @Size(min = 3, max = 500)
    private String comment;

    @NotNull(message = "Products ID is required")
    private Long productsId;

    @NotNull(message = "Rating is required")
    @Min(1)
    @Max(5)
    private Integer rating;
}
