package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
	List<Category> getAllCategoriess();

	void createCategory(Category category);

	String deleteCategory(Long categoryId);

	Category updateCategory(Category category, Long categoryId);
}
