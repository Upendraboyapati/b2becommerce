package com.b2b.service;

import com.b2b.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(UUID id);
    CategoryDTO updateCategory(UUID id, CategoryDTO categoryDTO);
    void deleteCategory(UUID id);
}
