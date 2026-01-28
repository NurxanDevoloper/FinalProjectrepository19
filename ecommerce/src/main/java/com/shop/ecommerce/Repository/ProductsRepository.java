package com.shop.ecommerce.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.ecommerce.Entity.Products;


public interface ProductsRepository extends JpaRepository<Products, Long> {

    List<Products> findByCategoryIgnoreCase(String category);

    List<Products> findByBrandIgnoreCase(String brand);

    List<Products> findByModelIgnoreCase(String model);

    List<Products> findBySizeIgnoreCase(String size);

    List<Products> findByColorIgnoreCase(String color);

    List<Products> findByMaterialIgnoreCase(String material);

    List<Products> findByPriceGreaterThanEqual(Double min);

    List<Products> findByPriceLessThanEqual(Double max);

    List<Products> findByQuantity(Integer quantity);
    
    List<Products> findByRating(Double rating);


    List<Products> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String name,
            String brand,
            String category
    );
    
    
    List<Products> findByOwnerId(Long ownerId);

    // Optional: check if a product exists by ID and owner ID
    boolean existsByIdAndOwnerId(Long id, Long ownerId);
}
