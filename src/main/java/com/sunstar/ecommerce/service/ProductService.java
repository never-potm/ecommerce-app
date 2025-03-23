package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Product;
import com.sunstar.ecommerce.payload.ProductDTO;
import com.sunstar.ecommerce.payload.ProductResponse;

public interface ProductService {
	ProductDTO addProduct(Long categoryId, Product product);

	ProductResponse getAllProducts();

	ProductResponse searchByCategory(Long categoryId);

	ProductResponse searchProductByKeyword(String keyword);
}
