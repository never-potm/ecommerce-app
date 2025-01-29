package com.sunstar.ecommerce.controller;

import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

	// we can either use @Autowired annotation or write a constructor
	@Autowired
	private CategoryService categoryService;

//	public CategoryController(CategoryService categoryService) {
//		this.categoryService = categoryService;
//	}

	@GetMapping("/api/public/categories")
	public List<Category> getAllCategories() {
		return categoryService.getAllCategoriess();
	}

	@PostMapping("/api/public/categories")
	public String createCategory(@RequestBody Category category) {
		categoryService.createCategory(category);
		return "Category created successfully";
	}

}
