package com.sunstar.ecommerce.controller;

import com.sunstar.ecommerce.config.AppConstants;
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
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
		CategoryResponse categories = categoryService.getAllCategoriess(pageNumber, pageSize);
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
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
		CategoryDTO categoryDTO = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}

	@PutMapping("/public/categories/{categoryId}")
//	@RequestMapping(method = RequestMethod.PUT, value = "/public/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
	                                                  @PathVariable Long categoryId) {
		CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
	}
}
