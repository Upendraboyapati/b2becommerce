package com.b2b.repository;

import com.b2b.entity.Product;
import com.b2b.entity.Category;
import com.b2b.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory(Category category);
    List<Product> findByOwner(User owner);
}
