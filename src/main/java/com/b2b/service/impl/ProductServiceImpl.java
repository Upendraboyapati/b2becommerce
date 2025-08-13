package com.b2b.service.impl;

import com.b2b.dto.ProductDTO;
import com.b2b.entity.Category;
import com.b2b.entity.Product;
import com.b2b.entity.User;
import com.b2b.mapper.ProductMapper;
import com.b2b.repository.CategoryRepository;
import com.b2b.repository.ProductRepository;
import com.b2b.repository.UserRepository;
import com.b2b.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = productMapper.toEntity(dto);

        // Fetch Category by name
        Category category = categoryRepository.findByName(dto.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + dto.getCategoryName()));

        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + dto.getOwnerId()));

        product.setCategory(category);
        product.setOwner(owner);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setCostPrice(dto.getCostPrice());
        existing.setQuantity(dto.getQuantity());
        existing.setImageUrl(dto.getImageUrl());

        // Optional: update category
        if (dto.getCategoryName() != null) {
            Category category = categoryRepository.findByName(dto.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found with name: " + dto.getCategoryName()));
            existing.setCategory(category);
        }

        return productMapper.toDTO(productRepository.save(existing));
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id)));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByOwner(UUID ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + ownerId));

        return productRepository.findByOwner(owner).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
