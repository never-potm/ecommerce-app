package com.sunstar.ecommerce.controller;

import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

	// we can either use @Autowired annotation or write a constructor
	@Autowired
	private CategoryService categoryService;

//	public CategoryController(CategoryService categoryService) {
//		this.categoryService = categoryService;
//	}

	@GetMapping("/public/categories")
//	@RequestMapping(method = RequestMethod.GET, value = "/public/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategoriess();
		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	@PostMapping("/public/categories")
//	@RequestMapping(method = RequestMethod.POST, value = "/public/categories")
	public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
		categoryService.createCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body("Category added");
	}

	@DeleteMapping("/admin/categories/{categoryId}")
//	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		try {
			String status = categoryService.deleteCategory(categoryId);
			return ResponseEntity.status(HttpStatus.OK).body(status);
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
		}
	}

	@PutMapping("/public/categories/{categoryId}")
//	@RequestMapping(method = RequestMethod.PUT, value = "/public/categories/{categoryId}")
	public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
		try {
			Category updatedCategory = categoryService.updateCategory(category, categoryId);
			return ResponseEntity.status(HttpStatus.OK).body("Category updated");
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
		}
	}
}
