package com.shop.ecommerce.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.ecommerce.Entity.Products;
import com.shop.ecommerce.Repository.ProductsRepository;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Products> getAll() {
        return productsRepository.findAll();
    }

    public Products getById(Long id) {
        return productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Products> getByCategory(String category) {
        return productsRepository.findByCategoryIgnoreCase(category);
    }

    public List<Products> getByBrand(String brand) {
        return productsRepository.findByBrandIgnoreCase(brand);
    }

    public List<Products> getByModel(String model) {
        return productsRepository.findByModelIgnoreCase(model);
    }

    public List<Products> getBySize(String size) {
        return productsRepository.findBySizeIgnoreCase(size);
    }

    public List<Products> getByColor(String color) {
        return productsRepository.findByColorIgnoreCase(color);
    }

    public List<Products> getByMaterial(String material) {
        return productsRepository.findByMaterialIgnoreCase(material);
    }

    public List<Products> getByMinPrice(Double min) {
        return productsRepository.findByPriceGreaterThanEqual(min);
    }

    public List<Products> getByMaxPrice(Double max) {
        return productsRepository.findByPriceLessThanEqual(max);
    }

    public List<Products> getByQuantity(Integer quantity) {
        return productsRepository.findByQuantity(quantity);
    }

    public List<Products> search(String keyword) {
        return productsRepository
                .findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                        keyword, keyword, keyword);
    }
    
    
    public List<Products> getByRating(Double rating) {
        return productsRepository.findByRating(rating);
    }
}
