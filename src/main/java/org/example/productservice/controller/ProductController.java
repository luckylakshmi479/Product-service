package org.example.productservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.ProductRequest;
import org.example.productservice.dto.ProductResponse;
import org.example.productservice.entity.Product;
import org.example.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request) {
        return productService.saveProduct(request);

    }

    @GetMapping
    public Page<ProductResponse> getProducts(

            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(defaultValue = "id")
            String sortBy) {
        {

            return productService.getProducts(
                    page,

                    size,
                    sortBy);
        }
    }
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(

            @RequestParam String name) {

        return productService
                .searchProducts(name);
    }


}


