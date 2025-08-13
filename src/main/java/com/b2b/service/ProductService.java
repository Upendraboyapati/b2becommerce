package com.b2b.service;

import com.b2b.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(UUID id, ProductDTO productDTO);
    ProductDTO getProductById(UUID id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsByOwner(UUID ownerId);
    void deleteProduct(UUID id);
}
