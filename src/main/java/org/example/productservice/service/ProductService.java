package org.example.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.ProductRequest;
import org.example.productservice.dto.ProductResponse;
import org.example.productservice.entity.Product;
import org.example.productservice.event.ProductCreatedEvent;
import org.example.productservice.mapper.ProductMapper;
import org.example.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductProducer productProducer;
    public ProductResponse saveProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);
            log.info("in Service");
        ProductCreatedEvent event =
                new ProductCreatedEvent(
                        savedProduct.getId(),
                        savedProduct.getName());
        productProducer.publish(event);

        return productMapper.toResponse(savedProduct);
    }
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public Page<ProductResponse> getProducts(
        int page,
        int size,
    String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository
                .findAll(pageable)
                .map(productMapper::toResponse);

    }
    public List<ProductResponse> searchProducts(
            String name) {
        return productRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(productMapper::toResponse)
                .toList();

    }

}

