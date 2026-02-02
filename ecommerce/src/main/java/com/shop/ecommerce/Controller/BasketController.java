package com.shop.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommerce.Service.BasketService;
import com.shop.ecommerce.dto.BasketProductsResponseDto;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/basket")
@Validated
public class BasketController {
	   @Autowired
	    private BasketService basketService;

	    @PostMapping("/{productsId}")
	    public void addToBasket(@PathVariable @Min(1) Long productsId) {
	        basketService.addToBasket(productsId);
	    }

	    @GetMapping
	    public List<BasketProductsResponseDto> myBaskets() {
	        return basketService.myBasket();
	    }

	    @DeleteMapping("/{productsId}")
	    public void removeFromBasket(@PathVariable @Min(1) Long productsId) {
	        basketService.removeFromBasket(productsId);
	    }
}
