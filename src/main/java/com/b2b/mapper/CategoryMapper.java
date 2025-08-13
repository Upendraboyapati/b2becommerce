package com.b2b.mapper;

import com.b2b.dto.CategoryDTO;
import com.b2b.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
