package com.produtos.servicos;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.client.dto.ProductDTO;
import com.produtos.domain.Product;
import com.produtos.dto.DTOConverter;
import com.produtos.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductDTO> getAll() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public List<ProductDTO> getProductByCategoryId(Long categoryId) {
		List<Product> products = productRepository.getProductByCategory(categoryId);
		return products.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public ProductDTO findByProductIdentifier(String productIdentifier) {
		Product product = productRepository.findByProductIdentifier(productIdentifier);
		if (product != null) {
			return DTOConverter.convert(product);
		}
		return null;
	}

	public ProductDTO save(ProductDTO productDTO) {
		Product product = productRepository.save(Product.convert(productDTO));
		return DTOConverter.convert(product);
	}

	public void delete(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			productRepository.delete(product.get());
		}
	}
	
	public ProductDTO editProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("produto não encontrado"));
		if(productDTO.getNome()!=null || !productDTO.getNome().isEmpty()) {
			product.setNome(productDTO.getNome());
		}
		if(productDTO.getPreco()!=null) {
			product.setPreco(productDTO.getPreco());
		}
		return DTOConverter.convert(productRepository.save(product));
	}
	
	public Page<ProductDTO> getAllPage(Pageable page){
		Page<Product> products = productRepository.findAll(page);
		return products.map(DTOConverter::convert);
	}
}
