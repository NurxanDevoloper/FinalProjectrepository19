package com.shop.ecommerce.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.ecommerce.Entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long>{
	List<Basket> findByUserId(Long userId);
	Optional<Basket> findByUserIdAndProductsId(Long userId,Long productsId);
	void deleteByUserIdAndProductsId(Long userId,Long productsId);
}
