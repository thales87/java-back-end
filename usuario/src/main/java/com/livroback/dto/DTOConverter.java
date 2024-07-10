package com.livroback.dto;

import com.client.dto.UserDTO;
import com.livroback.model.User;

public class DTOConverter {
	
	public static UserDTO convert(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setNome(user.getNome());
		userDTO.setCpf(user.getCpf());
		userDTO.setEndereco(user.getEndereco());
		userDTO.setEmail(user.getEmail());
		userDTO.setTelefone(user.getTelefone());
		userDTO.setDataCadastro(user.getDataCadastro());
		userDTO.setKey(user.getKey());
		return userDTO;
	}
}
