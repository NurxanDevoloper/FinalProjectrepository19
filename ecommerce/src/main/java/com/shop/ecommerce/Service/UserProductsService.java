package com.shop.ecommerce.Service;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.ecommerce.Entity.Products;
import com.shop.ecommerce.Entity.User;
import com.shop.ecommerce.Repository.ProductsRepository;
import com.shop.ecommerce.Repository.UserRepository;
import com.shop.ecommerce.dto.ProductsDto;

@Service
@Transactional
public class UserProductsService {

    private final UserRepository userRepository;
    private final ProductsRepository productsRepository;

    public UserProductsService(UserRepository userRepository,
                               ProductsRepository productsRepository) {
        this.userRepository = userRepository;
        this.productsRepository = productsRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Products saveProducts(ProductsDto dto) {
        User user = getCurrentUser();

        Products products = new Products();
        mapDtoToEntity(dto, products);
        products.setOwnerId(user.getId());

        return productsRepository.save(products);
    }

    public List<Products> getMyProducts() {
        return productsRepository.findByOwnerId(getCurrentUser().getId());
    }

    public Products updateProducts(Long id, ProductsDto dto) {
        User user = getCurrentUser();

        // Проверяем, существует ли продукт и принадлежит ли он юзеру
        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!products.getOwnerId().equals(user.getId())) {
            throw new RuntimeException("You can't update another user's product");
        }

        mapDtoToEntity(dto, products);
        return productsRepository.save(products);
    }

    public void deleteProducts(Long id) {
        User user = getCurrentUser();
        
        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!products.getOwnerId().equals(user.getId())) {
            throw new RuntimeException("You can't delete another user's product");
        }

        productsRepository.delete(products);
    }

    // Вспомогательный метод, чтобы не дублировать код
    private void mapDtoToEntity(ProductsDto dto, Products products) {
        products.setName(dto.getName());
        products.setBrand(dto.getBrand());
        products.setCategory(dto.getCategory());
        products.setModel(dto.getModel());
        products.setSize(dto.getSize());
        products.setColor(dto.getColor());
        products.setMaterial(dto.getMaterial());
        products.setPrice(dto.getPrice());
        products.setQuantity(dto.getQuantity());
        products.setImgUrl(dto.getImgUrl());
        products.setRating(dto.getRating());
    }
}