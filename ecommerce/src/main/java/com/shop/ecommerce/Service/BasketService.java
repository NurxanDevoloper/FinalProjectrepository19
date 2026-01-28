package com.shop.ecommerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shop.ecommerce.Entity.Basket;
import com.shop.ecommerce.Entity.Products;
import com.shop.ecommerce.Entity.User;
import com.shop.ecommerce.Repository.BasketRepository;
import com.shop.ecommerce.Repository.ProductsRepository;
import com.shop.ecommerce.Repository.UserRepository;
import com.shop.ecommerce.dto.BasketProductsResponseDto;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    public void addToBasket(Long productsId) {
        User user = getCurrentUser();

        Products products = productsRepository.findById(productsId)
                .orElseThrow(() -> new RuntimeException("products not found"));

        Basket basket = basketRepository
                .findByUserIdAndProductsId(user.getId(), productsId)
                .orElse(null);

        if (basket == null) {
            Basket newBasket = new Basket();
            newBasket.setUserId(user.getId());
            newBasket.setProductsId(productsId);
            newBasket.setQuantity(1);
            basketRepository.save(newBasket);
        } else {
            basket.setQuantity(basket.getQuantity() + 1);
            basketRepository.save(basket);
        }
    }

    public List<BasketProductsResponseDto> myBasket() {
        User user = getCurrentUser();
        List<Basket> baskets=basketRepository.findByUserId(user.getId());
        
        return baskets.stream().map(basket->{
        	Products products = productsRepository.findById(basket.getProductsId())
        			.orElseThrow(()-> new RuntimeException("products not found"));
        	
        	
        	 BasketProductsResponseDto dto = new BasketProductsResponseDto();
             dto.setBasketId(basket.getId());
             dto.setBrand(products.getBrand());
             dto.setModel(products.getModel());             
             
             dto.setPrice(products.getPrice());
             dto.setImgUrl(products.getImgUrl());
             dto.setQuantity(basket.getQuantity());
             return dto;
        }).toList();
       
    }

    public void removeFromBasket(Long productsId) {
        User user = getCurrentUser();

        Basket basket = basketRepository
                .findByUserIdAndProductsId(user.getId(), productsId)
                .orElseThrow(() -> new RuntimeException("products not found"));

        basketRepository.delete(basket);
    }
}
