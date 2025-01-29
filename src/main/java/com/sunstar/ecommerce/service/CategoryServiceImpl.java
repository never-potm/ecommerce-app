package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

	private List<Category> categories = new ArrayList<>();

	@Override
	public List<Category> getAllCategoriess() {
		return categories;
	}

	@Override
	public void createCategory(Category category) {
		categories.add(category);
	}
}
