package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.exceptions.APIException;
import com.sunstar.ecommerce.exceptions.ResourceNotFoundException;
import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategoriess() {
		return categoryRepository.findAll();
	}

	@Override
	public void createCategory(Category category) {
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (savedCategory != null) {
			throw new APIException("Category with name " + category.getCategoryName() + " already exists");
		}
		categoryRepository.save(category);
	}

	public String deleteCategory(Long categoryId) {

		Optional<Category> category = categoryRepository.findById(categoryId);

		Category existingCategory = category.orElseThrow(
				() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		categoryRepository.delete(existingCategory);
		return "Category with categoryId " + categoryId + " deleted successfully";
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {

		Optional<Category> savedCategory = categoryRepository.findById(categoryId);
		Category existingCategory = savedCategory.orElseThrow(
				() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		category.setCategoryId(categoryId);

		return categoryRepository.save(existingCategory);
	}
}
