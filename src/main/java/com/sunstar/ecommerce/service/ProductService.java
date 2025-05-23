package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.payload.ProductDTO;
import com.sunstar.ecommerce.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
	ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

	ProductResponse getAllProducts();

	ProductResponse searchByCategory(Long categoryId);

	ProductResponse searchProductByKeyword(String keyword);

	ProductDTO updateProduct(Long productId, ProductDTO productDTO);

	ProductDTO deleteProduct(Long productId);

	ProductDTO updateProductImage(Long productId, MultipartFile image)
			throws IOException;
}
