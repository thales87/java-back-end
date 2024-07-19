package com.shop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.client.dto.ProductDTO;
import com.client.exception.ProductNotFoundException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

	@Value("${PRODUCT_API_URL:http://localhost:8081}")
	private String productAPIURL;
	
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
