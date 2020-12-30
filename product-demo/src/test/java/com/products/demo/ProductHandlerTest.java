package com.products.demo;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.products.demo.ProductApp;
import com.products.demo.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = ProductApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductHandlerTest {

	private static WebTestClient webclient;
	private static Instant now = Instant.now();
	private static Product item = ProductHandlerTest.generateProductItem(1000L);

	@LocalServerPort
	int port;

	@Autowired
	public void setApplicationContext(ApplicationContext context) {
		webclient = WebTestClient.bindToApplicationContext(context).configureClient().baseUrl("/products").build();
	}

	@Test
	@Order(10)
	public void testGetAllProducts() {

		webclient.get().uri("/").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBody().jsonPath("$.[0].productid").isNotEmpty()
				.jsonPath("$.[0].title").isNotEmpty().jsonPath("$.[0].description").isNotEmpty();
	}

	@Test
	@Order(40)
	public void testCreateProduct() {
		Product item = ProductHandlerTest.generateProductItem(1000L);
		item.setProductid(null);

		webclient.post().uri("/add").contentType(MediaType.APPLICATION_JSON).body(Mono.just(item), Product.class)
				.exchange().expectStatus().isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON);
	}

	private void createProduct() {
		Product item = ProductHandlerTest.generateProductItem(1000L);
		item.setProductid(null);

		webclient.post().uri("/add").contentType(MediaType.APPLICATION_JSON).body(Mono.just(item), Product.class)
				.exchange().expectStatus().isCreated();
	}

	private String replaceProduct(String path) {
		return path.replaceAll("\\{productid\\}", item.getProductid().toString());
	}

	private static Product generateProductItem(Long id) {
		Product item = new Product();
		item.setProductid(1000l);
		item.setTitle("Product Title");
		item.setDescription("Product Desc");
		item.setBrand("Product brand");
		item.setPrice(100.0);
		item.setColor("Red");
		item.setCreatedOn(now);

		return item;
	}

	public static List<Product> generateProductItemList() {
		return LongStream.range(1, 100).mapToObj(value -> {
			return generateProductItem(value);
		}).collect(Collectors.toList());
	}
}
