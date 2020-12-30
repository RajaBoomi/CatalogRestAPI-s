package com.products.demo.config;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.products.demo.handler.ProductHandler;

@Configuration
public class ProductRouter {

	@RouterOperations({ @RouterOperation(path = "/products", beanClass = ProductHandler.class, beanMethod = "findAll"),
			@RouterOperation(path = "/products/{productid}", beanClass = ProductHandler.class, beanMethod = "getProductById"),
			@RouterOperation(path = "/products/add", beanClass = ProductHandler.class, beanMethod = "addProduct"),
			@RouterOperation(path = "/products/{productid}", beanClass = ProductHandler.class, beanMethod = "deleteProductById"),
			@RouterOperation(path = "/products/search", beanClass = ProductHandler.class, beanMethod = "findByTitleAndDescription"),
			@RouterOperation(path = "/products/version", beanClass = ProductHandler.class, beanMethod = "checkVersion"),
			@RouterOperation(path = "/products/bulk", beanClass = ProductHandler.class, beanMethod = "bulkProduct"),
			@RouterOperation(path = "/products/update", beanClass = ProductHandler.class, beanMethod = "updateProduct")

	})
	@Bean
	public RouterFunction<ServerResponse> root(ProductHandler productHandler) {
		return RouterFunctions.route().GET("/", productHandler::getAllProducts)
				.GET("/products", productHandler::findAll)
				.GET("/products/search", RequestPredicates.contentType(MediaType.APPLICATION_JSON),
						productHandler::findByTitleAndDescription)
				.GET("/products/version", productHandler::checkVersion)
				.GET("/products/{productid}", RequestPredicates.accept(MediaType.TEXT_PLAIN),
						productHandler::getProductById)
				.POST("/products/add", RequestPredicates.contentType(MediaType.APPLICATION_JSON),
						productHandler::addProduct)
				.POST("/products/bulk", RequestPredicates.contentType(MediaType.APPLICATION_JSON),
						productHandler::bulkProduct)
				.PUT("/products/update", RequestPredicates.contentType(MediaType.APPLICATION_JSON),
						productHandler::updateProduct)
				.DELETE("/products/{productid}", RequestPredicates.accept(MediaType.TEXT_PLAIN),
						productHandler::deleteProductById)
				.build();
	}

}