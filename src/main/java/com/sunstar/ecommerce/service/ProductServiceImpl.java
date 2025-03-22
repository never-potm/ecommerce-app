package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.exceptions.ResourceNotFoundException;
import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.model.Product;
import com.sunstar.ecommerce.payload.ProductDTO;
import com.sunstar.ecommerce.repositories.CategoryRepository;
import com.sunstar.ecommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO addProduct(Long categoryId, Product product) {

		Category category = categoryRepository.findById(categoryId).
		                                      orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",
		                                                                                      categoryId));

		product.setImage("default.png");
		product.setCategory(category);
		double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());

		product.setSpecialPrice(specialPrice);
		Product savedProduct = productRepository.save(product);

		return modelMapper.map(savedProduct, ProductDTO.class);
	}
}
