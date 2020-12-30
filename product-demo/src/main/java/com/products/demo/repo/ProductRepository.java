package com.products.demo.repo;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.products.demo.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveSortingRepository<Product, Long> {

	Mono<Product> findByProductid(long productid);

	Flux<Product> findByTitleContainingIgnoreCase(String title);

	Flux<Product> findByDescriptionContainingIgnoreCase(String description);

	Flux<Product> findByTitleContainingAndDescriptionContainingIgnoreCase(String title, String description);

}
