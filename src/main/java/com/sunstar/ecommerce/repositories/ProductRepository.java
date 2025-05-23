package com.sunstar.ecommerce.repositories;

import com.sunstar.ecommerce.model.Category;
import com.sunstar.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategoryOrderByPriceAsc(Category category);

	List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
