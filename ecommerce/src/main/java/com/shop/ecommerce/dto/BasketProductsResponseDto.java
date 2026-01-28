package com.shop.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BasketProductsResponseDto {
    @NotNull
    private Long basketId;

    @NotBlank
    private String brand;

    @NotNull
    @Positive
    private Double price;

    @NotBlank
    private String model;

    @NotBlank
    private String imgUrl;

    @NotNull
    @Min(1)
    private Integer quantity;
}
