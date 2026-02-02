package com.shop.ecommerce.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shop.ecommerce.Entity.Products;
import com.shop.ecommerce.Service.ProductsService;
import com.shop.ecommerce.Service.UserProductsService;
import com.shop.ecommerce.dto.ProductsDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class ProductsController {

    private final ProductsService productsService;
    private final UserProductsService userProductsService;

    public ProductsController(ProductsService productsService,
                              UserProductsService userProductsService) {
        this.productsService = productsService;
        this.userProductsService = userProductsService;
    }

    // ====== BASIC CRUD ======

    @GetMapping
    public List<Products> getAll() {
        return productsService.getAll();
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<Products> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.getById(id));
    }

    // ====== CREATE ======
    @PostMapping("/addProducts")
    public ResponseEntity<Products> create(
            @Valid @RequestBody ProductsDto dto) {

        return ResponseEntity.ok(userProductsService.saveProducts(dto));
    }

    // ====== UPDATE ======
    @PutMapping("/update/{id}")
    public ResponseEntity<Products> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductsDto dto) {

        return ResponseEntity.ok(userProductsService.updateProducts(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userProductsService.deleteProducts(id);
        return ResponseEntity.noContent().build();
    }

    // ====== CURRENT USER PRODUCTS ======

    @GetMapping("/my")
    public ResponseEntity<List<Products>> getMyProducts() {
        return ResponseEntity.ok(userProductsService.getMyProducts());
    }

    // ====== FILTERS ======

    // 🔥 ВАЖНО: Double, а не Integer
    @GetMapping("/rating/{rating}")
    public List<Products> getByRating(@PathVariable Double rating) {
        return productsService.getByRating(rating);
    }

    @GetMapping("/category/{category}")
    public List<Products> getByCategory(@PathVariable String category) {
        return productsService.getByCategory(category);
    }

    @GetMapping("/brand/{brand}")
    public List<Products> getByBrand(@PathVariable String brand) {
        return productsService.getByBrand(brand);
    }

    @GetMapping("/model/{model}")
    public List<Products> getByModel(@PathVariable String model) {
        return productsService.getByModel(model);
    }

    @GetMapping("/size/{size}")
    public List<Products> getBySize(@PathVariable String size) {
        return productsService.getBySize(size);
    }

    @GetMapping("/color/{color}")
    public List<Products> getByColor(@PathVariable String color) {
        return productsService.getByColor(color);
    }

    @GetMapping("/material/{material}")
    public List<Products> getByMaterial(@PathVariable String material) {
        return productsService.getByMaterial(material);
    }

    @GetMapping("/price/min/{min}")
    public List<Products> getByMinPrice(@PathVariable Double min) {
        return productsService.getByMinPrice(min);
    }

    @GetMapping("/price/max/{max}")
    public List<Products> getByMaxPrice(@PathVariable Double max) {
        return productsService.getByMaxPrice(max);
    }

    @GetMapping("/quantity/{quantity}")
    public List<Products> getByQuantity(@PathVariable Integer quantity) {
        return productsService.getByQuantity(quantity);
    }

    // ====== SEARCH ======

    @GetMapping("/search")
    public List<Products> search(@RequestParam String keyword) {
        return productsService.search(keyword);
    }
}
