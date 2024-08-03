package com.produtos.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.client.dto.ProductDTO;
import com.produtos.servicos.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public List<ProductDTO> getProducts() {
		return productService.getAll();
	}

	@GetMapping("/category/{categoryId}")
	public List<ProductDTO> getProductByCategory(@PathVariable Long categoryId) {
		return productService.getProductByCategoryId(categoryId);
	}

	@GetMapping("/{productIdentifier}")
	public ProductDTO getProductByIdentifier(@PathVariable String productIdentifier) {
		return productService.findByProductIdentifier(productIdentifier);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		productService.delete(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProductDTO inserir(@RequestBody @Valid ProductDTO productDTO) {
		return productService.save(productDTO);
	}

	@PatchMapping("/{id}")
	public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
		return productService.editProduct(id, productDTO);
	}

	@GetMapping("/pageable")
	public Page<ProductDTO> getProductsPage(Pageable pageable) {
		return productService.getAllPage(pageable);
	}
}
