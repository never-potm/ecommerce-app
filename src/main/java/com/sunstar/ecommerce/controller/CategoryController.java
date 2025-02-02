package com.sunstar.ecommerce.controller;

import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.payload.CategoryDTO;
import com.sunstar.ecommerce.payload.CategoryResponse;
import com.sunstar.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<CategoryResponse> getAllCategories() {
		CategoryResponse categories = categoryService.getAllCategoriess();
		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	@PostMapping("/public/categories")
//	@RequestMapping(method = RequestMethod.POST, value = "/public/categories")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/admin/categories/{categoryId}")
//	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		String status = categoryService.deleteCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(status);
	}

	@PutMapping("/public/categories/{categoryId}")
//	@RequestMapping(method = RequestMethod.PUT, value = "/public/categories/{categoryId}")
	public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId) {
		Category updatedCategory = categoryService.updateCategory(category, categoryId);
		return ResponseEntity.status(HttpStatus.OK).body("Category updated");
	}
}
