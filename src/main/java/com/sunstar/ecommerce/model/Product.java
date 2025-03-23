package com.sunstar.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	@NotBlank
	@Size(min = 5, message = "Product name must contain atleast 5 characters")
	private String productName;
	private String image;
	@NotBlank
	@Size(min = 10, message = "Product description must contain atleast 10 characters")
	private String description;
	private Integer quantity;
	private Double price;
	private Double discount;
	private Double specialPrice;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
}
