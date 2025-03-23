package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.payload.ProductDTO;
import com.sunstar.ecommerce.payload.ProductResponse;

public interface ProductService {
	ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

	ProductResponse getAllProducts();

	ProductResponse searchByCategory(Long categoryId);

	ProductResponse searchProductByKeyword(String keyword);

	ProductDTO updateProduct(Long productId, ProductDTO productDTO);

	ProductDTO deleteProduct(Long productId);
}
