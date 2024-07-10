package com.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.client.dto.ProductDTO;
import com.client.exception.ProductNotFoundException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

	private String productAPIURL="http://localhost:8081";
	
	public ProductDTO getProductByIdentifier(String productIdentifier) {
		try {
			WebClient webCliente = WebClient.builder()
					.baseUrl(productAPIURL)
					.build();
			
			Mono<ProductDTO> product = webCliente.get()
					.uri("/product/"+productIdentifier)
					.retrieve()
					.bodyToMono(ProductDTO.class);
			
			return product.block();
		}catch (Exception e) {
			throw new ProductNotFoundException();
		}
	}
	
}
