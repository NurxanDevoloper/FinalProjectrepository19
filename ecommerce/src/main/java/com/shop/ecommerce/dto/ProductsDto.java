package com.shop.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductsDto {

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    @NotBlank
    private String category;

    @NotBlank
    private String model;

    @NotBlank
    private String size;

    @NotBlank
    private String color;

    @NotBlank
    private String material;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotBlank
    @Pattern(regexp = "^(http|https)://.*$")
    private String imgUrl;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating;
}
