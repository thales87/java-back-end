package com.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private Long id;
	private String productIdentifier;
	private String nome;
	private String descricao;
	private Float preco;
	private Byte[] imagem;
	private CategoryDTO categoryDTO;
}
