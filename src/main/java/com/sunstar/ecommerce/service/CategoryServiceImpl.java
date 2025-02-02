package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.exceptions.APIException;
import com.sunstar.ecommerce.exceptions.ResourceNotFoundException;
import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.payload.CategoryDTO;
import com.sunstar.ecommerce.payload.CategoryResponse;
import com.sunstar.ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryResponse getAllCategoriess() {
		List<Category> allCategories = categoryRepository.findAll();
		if (allCategories.isEmpty()) {
			throw new APIException("No categories found");
		}

		List<CategoryDTO> categoryDTOS = allCategories.stream()
		                                      .map(category -> modelMapper.map(category, CategoryDTO.class))
		                                      .toList();

		CategoryResponse allCategoriesResponse = new CategoryResponse();
		allCategoriesResponse.setContent(categoryDTOS);

		return allCategoriesResponse;
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category categoryFromDB = categoryRepository.findByCategoryName(category.getCategoryName());
		if (categoryFromDB != null) {
			throw new APIException("Category with name " + category.getCategoryName() + " already exists");
		}

		Category savedCategory = categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	public CategoryDTO deleteCategory(Long categoryId) {

		Optional<Category> category = categoryRepository.findById(categoryId);

		Category existingCategory = category.orElseThrow(
				() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		categoryRepository.delete(existingCategory);
		return modelMapper.map(existingCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

		Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Category categoryToUpdate = modelMapper.map(categoryDTO, Category.class);
		categoryToUpdate.setCategoryId(categoryId);

		savedCategory = categoryRepository.save(categoryToUpdate);
		return modelMapper.map(savedCategory, CategoryDTO.class);
	}
}
