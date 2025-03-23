package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.exceptions.ResourceNotFoundException;
import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.model.Product;
import com.sunstar.ecommerce.payload.ProductDTO;
import com.sunstar.ecommerce.payload.ProductResponse;
import com.sunstar.ecommerce.repositories.CategoryRepository;
import com.sunstar.ecommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public ProductResponse getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductDTO> productDTOList = products.stream()
		                                          .map(product -> modelMapper.map(product, ProductDTO.class))
		                                          .toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOList);

		return productResponse;
	}

	@Override
	public ProductResponse searchByCategory(Long categoryId) {

		Category category =
				categoryRepository.findById(categoryId)
				                  .orElseThrow(
						                  () -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		List<Product> categoryProducts = productRepository.findByCategoryOrderByPriceAsc(category);

		List<ProductDTO> productDTOList = categoryProducts.stream()
		                                                  .map(product -> modelMapper.map(product, ProductDTO.class))
		                                                  .toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOList);

		return productResponse;
	}

	@Override
	public ProductResponse searchProductByKeyword(String keyword) {

		List<Product> categoryProducts = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');

		List<ProductDTO> productDTOList = categoryProducts.stream()
		                                                  .map(product -> modelMapper.map(product, ProductDTO.class))
		                                                  .toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOList);

		return productResponse;
	}

	@Override
	public ProductDTO updateProduct(Long productId, Product product) {

		Product existingProduct = productRepository.findById(productId)
		                                           .orElseThrow(
				                                           () -> new ResourceNotFoundException("Product", "productId",
				                                                                               productId));

		existingProduct.setProductName(product.getProductName());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setDiscount(product.getDiscount());
		existingProduct.setSpecialPrice(product.getSpecialPrice());

		Product savedProduct = productRepository.save(existingProduct);

		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO deleteProduct(Long productId) {
		Product existingProduct = productRepository.findById(productId)
		                                           .orElseThrow(
				                                           () -> new ResourceNotFoundException("Product", "productId",
				                                                                               productId));

		productRepository.delete(existingProduct);

		return modelMapper.map(existingProduct, ProductDTO.class);
	}
}
