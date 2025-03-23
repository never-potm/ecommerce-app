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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

		Category category = categoryRepository.findById(categoryId).
		                                      orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",
		                                                                                      categoryId));

		Product product = modelMapper.map(productDTO, Product.class);
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
	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

		Product existingProduct = productRepository.findById(productId)
		                                           .orElseThrow(
				                                           () -> new ResourceNotFoundException("Product", "productId",
				                                                                               productId));

		Product product = modelMapper.map(productDTO, Product.class);
		if (product.getProductName() != null) {
			existingProduct.setProductName(product.getProductName());
		}
		if (product.getDescription() != null) {
			existingProduct.setDescription(product.getDescription());
		}
		if (product.getPrice() != null) {
			existingProduct.setPrice(product.getPrice());
		}
		if (product.getDiscount() != null) {
			existingProduct.setDiscount(product.getDiscount());
		}
		if (product.getSpecialPrice() != null) {
			existingProduct.setSpecialPrice(product.getSpecialPrice());
		}

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

	@Override
	public ProductDTO updateProductImage(Long productId, MultipartFile image)
			throws IOException {

		Product product = productRepository.findById(productId)
		                                           .orElseThrow(
				                                           () -> new ResourceNotFoundException("Product", "productId",
				                                                                               productId));

		String path = "images/";
		String fileName = uploadImage(path, image);

		product.setImage(fileName);

		Product updatedProduct = productRepository.save(product);

		return modelMapper.map(updatedProduct, ProductDTO.class);
	}

	private String uploadImage(String path, MultipartFile imageFile)
			throws IOException {
		String originalFilename = imageFile.getOriginalFilename();

		String randomId = UUID.randomUUID().toString();

		String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));

		String filePath = path + File.pathSeparator + fileName;

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}

		Files.copy(imageFile.getInputStream(), Paths.get(filePath));

		return fileName;
	}
}
