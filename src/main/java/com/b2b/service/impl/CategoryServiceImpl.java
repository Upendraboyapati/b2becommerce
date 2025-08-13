package com.b2b.service.impl;

import com.b2b.dto.CategoryDTO;
import com.b2b.entity.Category;
import com.b2b.mapper.CategoryMapper;
import com.b2b.repository.CategoryRepository;
import com.b2b.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) {
        return categoryMapper.toDTO(categoryRepository.save(categoryMapper.toEntity(dto)));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(UUID id) {
        return categoryMapper.toDTO(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public CategoryDTO updateCategory(UUID id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
