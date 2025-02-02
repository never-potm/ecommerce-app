package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.payload.CategoryDTO;
import com.sunstar.ecommerce.payload.CategoryResponse;

public interface CategoryService {
	CategoryResponse getAllCategoriess();

	CategoryDTO createCategory(CategoryDTO categoryDTO);

	CategoryDTO deleteCategory(Long categoryId);

	CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
