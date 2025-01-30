package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
		categoryRepository.save(category);
	}

	public String deleteCategory(Long categoryId) {

		List<Category> categories = categoryRepository.findAll();

		Category category = categories.stream().
		                              filter(c -> c.getCategoryId().equals(categoryId)).
		                              findFirst().
		                              orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

		categoryRepository.delete(category);
		return "Category with categoryId " + categoryId + " deleted successfully";
	}

	@Override
	public Category updateCategory(Category category, Long categoryId) {

		List<Category> categories = categoryRepository.findAll();

		Optional<Category> optionalCategory = categories.stream().
		                                                filter(c -> c.getCategoryId().equals(categoryId)).
		                                                findFirst();

		if (optionalCategory.isPresent()) {
			Category existingCategory = optionalCategory.get();
			existingCategory.setCategoryName(category.getCategoryName());

			return categoryRepository.save(existingCategory);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
		}
	}
}
