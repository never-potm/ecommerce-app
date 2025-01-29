package com.sunstar.ecommerce.controller;

import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategoriess();
		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	@PostMapping("/api/public/categories")
	public ResponseEntity<String> createCategory(@RequestBody Category category) {
		categoryService.createCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body("Category added");
	}

	@DeleteMapping("/api/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		try {
			String status = categoryService.deleteCategory(categoryId);
			return ResponseEntity.status(HttpStatus.OK).body(status);
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
		}
	}
}
