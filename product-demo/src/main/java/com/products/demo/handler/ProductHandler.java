package com.products.demo.handler;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.products.demo.model.Product;
import com.products.demo.repo.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Handler for Product CRUD operations.
 *
 * @author Raja Boominathan
 */

@Component
public class ProductHandler {

	private final ProductRepository productRepo;

	ProductHandler(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	/*
	 * 
	 * To get all the products
	 */

	public Mono<ServerResponse> getAllProducts(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findAll(), Product.class);
	}

	/*
	 * 
	 * To get product by product id
	 * 
	 * @Param productid
	 */
	public Mono<ServerResponse> getProductById(ServerRequest request) {
		long id = Integer.parseInt(request.pathVariable("productid"));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findById(id),
				Product.class);

	}

	/*
	 * 
	 * To add product
	 * 
	 * @Param Product
	 */
	public Mono<ServerResponse> addProduct(ServerRequest request) {
		return request.bodyToMono(Product.class).flatMap(pdt -> {
			pdt.setCreatedOn(Instant.now());
			return Mono.just(pdt);
		}).flatMap(product -> productRepo.save(product))
				.flatMap(newProduct -> ServerResponse.created(URI.create("/products/" + newProduct.getProductid()))
						.contentType(MediaType.APPLICATION_JSON).build());
	}

	/*
	 * 
	 * To update product
	 * 
	 * @Param Product
	 */
	public Mono<ServerResponse> updateProduct(ServerRequest request) {
		Mono<Product> updateproduct = request.bodyToMono(Product.class);

		Mono<Product> updatedproduct = updateproduct.map(f -> new Product(f.getProductid(), f.getTitle(),
				f.getDescription(), f.getBrand(), f.getPrice(), f.getColor(), Instant.now(), Instant.now()))
				.flatMap(productRepo::save);

		updateproduct = Mono.from(updatedproduct.map(f -> {
			return new Product(f.getProductid(), f.getTitle(), f.getDescription(), f.getBrand(), f.getPrice(),
					f.getColor(), f.getUpdatedOn(), f.getCreatedOn());
		}));
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(updateproduct, Product.class);

	}

	/*
	 * 
	 * To delete product
	 * 
	 * @Param ProductId
	 */
	public Mono<ServerResponse> deleteProductById(ServerRequest request) {
		long id = Integer.parseInt(request.pathVariable("productid"));
		Mono<Product> deleteproduct = productRepo.findById(id);
		deleteproduct.subscribe(value -> {
			productRepo.delete(value).subscribe();
		});
		return ServerResponse.ok().build();

	}

	/*
	 * 
	 * To find product by title and description
	 * 
	 * @Param Title
	 * 
	 * @param Description
	 */
	public Mono<ServerResponse> findByTitleAndDescription(ServerRequest request) {
		Optional<String> title = request.queryParam("title");
		Optional<String> description = request.queryParam("description");
		if (title != null && title.isPresent() && description != null && description.isPresent())
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo
					.findByTitleContainingAndDescriptionContainingIgnoreCase(title.orElse(""), description.orElse("")),
					Product.class);
		else if (title != null && title.isPresent())
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
					.body(productRepo.findByTitleContainingIgnoreCase(title.orElse("")), Product.class);
		else if (description != null && description.isPresent())
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
					.body(productRepo.findByDescriptionContainingIgnoreCase(description.orElse("")), Product.class);
		else
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findAll(),
					Product.class);

	}

	/*
	 * 
	 * To find products using sorting and pagination
	 * 
	 * @Param Page,Size,Sortname,Type
	 */
	public Mono<ServerResponse> findAll(ServerRequest request) {
		int page = Integer.parseInt(request.queryParam("page").orElse("0"));
		int size = Integer.parseInt(request.queryParam("size").orElse("10"));
		String sortname = request.queryParam("sortname").orElse("productid");
		String type = request.queryParam("type").orElse("DESC");

		Sort sort = Sort.by(Sort.Direction.valueOf(type.toUpperCase()), sortname);

		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(productRepo.findAll(sort).skip(page * size).take(size), Product.class);

	}
	/*
	 * 
	 * To insert bulk products
	 * 
	 * @Param List of Product
	 */

	public Mono<ServerResponse> bulkProduct(ServerRequest request) {
		Flux<Product> product = request.bodyToFlux(Product.class);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.saveAll(product),
				Product.class);
	}

	/*
	 * 
	 * To check versioned API by using header
	 * 
	 * @Header API-Version
	 */
	public Mono<ServerResponse> checkVersion(ServerRequest request) {
		final String apiVersion = request.headers().header("API-Version").get(0);

		switch (apiVersion) {
		case "1.0.0":
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findAll().last(),
					Product.class);
		case "1.0.1":
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findAll().count(),
					Product.class);
		default:
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productRepo.findAll(),
					Product.class);
		}
	}

}
