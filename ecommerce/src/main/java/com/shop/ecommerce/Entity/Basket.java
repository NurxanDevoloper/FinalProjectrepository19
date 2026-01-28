package com.shop.ecommerce.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "basket")
@Data
public class Basket {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull(message = "User ID is required")
	    private Long userId;

	    @NotNull(message = "Products ID is required")
	    private Long productsId;

	    @NotNull(message = "Quantity is required")
	    @Min(value = 1, message = "Quantity must be at least 1")
	    @Max(value = 100, message = "Quantity cannot be more than 100")
	    private Integer quantity;
}
