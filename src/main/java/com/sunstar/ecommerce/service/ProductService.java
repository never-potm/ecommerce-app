package com.sunstar.ecommerce.service;

import com.sunstar.ecommerce.model.Product;
import com.sunstar.ecommerce.payload.ProductDTO;

public interface ProductService {
	ProductDTO addProduct(Long categoryId, Product product);
}
