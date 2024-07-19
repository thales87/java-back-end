package com.produtos.dto;

import com.client.dto.CategoryDTO;
import com.client.dto.ProductDTO;
import com.produtos.domain.Category;
import com.produtos.domain.Product;

public class DTOConverter {

	public static CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setNome(category.getNome());
		return categoryDTO;
	}
	
	public static ProductDTO convert(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId()!=null?product.getId():null);
		productDTO.setNome(product.getNome());
		productDTO.setDescricao(product.getDescricao());
		productDTO.setPreco(product.getPreco());
		productDTO.setProductIdentifier(product.getProductIdentifier());
		if(product.getCategory()!=null) {
			productDTO.setCategoryDTO(DTOConverter.convert(product.getCategory()));
		}
		return productDTO;
	}
}
