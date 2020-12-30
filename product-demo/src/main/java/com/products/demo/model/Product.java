package com.products.demo.model;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Table
public class Product {

	@Id
	private Long productid;

	public Product(long productid, String title, String description, String brand, double price, String color,
			Instant updatedOn, Instant createdOn) {
		this.productid = productid;
		this.title = title;
		this.description = description;
		this.brand = brand;
		this.price = price;
		this.color = color;
		this.updatedOn = updatedOn;
		this.createdOn = createdOn;
	}

	@NotEmpty(message = "Title cannot be null or empty")
	private String title;

	@NotEmpty(message = "Description cannot be null or empty")
	@NonNull
	private String description;

	@NonNull
	private String brand;

	@NotNull(message = "Price cannot be null or empty")
	@NonNull
	private Double price;

	@NonNull
	private String color;

	@NonNull
	private Instant createdOn;

	private Instant updatedOn;
}
