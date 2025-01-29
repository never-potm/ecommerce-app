package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
	List<Category> getAllCategoriess();
	void createCategory(Category category);
}
