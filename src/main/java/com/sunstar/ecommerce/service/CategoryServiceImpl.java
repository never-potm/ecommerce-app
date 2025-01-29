package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

	private List<Category> categories = new ArrayList<>();
	private Long nextId = 1L;

	@Override
	public List<Category> getAllCategoriess() {
		return categories;
	}

	@Override
	public void createCategory(Category category) {
		category.setCategoryId(nextId++);
		categories.add(category);
	}

	public String deleteCategory(Long categoryId) {
		Category category = categories.stream().
		                              filter(c -> c.getCategoryId().equals(categoryId)).
		                              findFirst().
		                              orElse(null);

		if (category == null) {
			return "category with categoryId " + categoryId + " not found";
		}

		categories.remove(category);
		return "Category with categoryId " + categoryId + " deleted successfully";
	}
}
