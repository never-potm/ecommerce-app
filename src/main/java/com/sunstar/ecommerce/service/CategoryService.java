package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.payload.CategoryResponse;

public interface CategoryService {
	CategoryResponse getAllCategoriess();

	void createCategory(Category category);

	String deleteCategory(Long categoryId);

	Category updateCategory(Category category, Long categoryId);
}
