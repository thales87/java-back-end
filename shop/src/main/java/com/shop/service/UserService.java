package com.shop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.client.dto.UserDTO;
import com.client.exception.UserNotFoundException;

import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Value("${USER_API_URL:http://localhost:8080}")
	private String userAPIURL;
	
	public UserDTO getUserByCpf(String cpf,String key) {
		try {
			WebClient webCliente = WebClient.builder()
					.baseUrl(userAPIURL)
					.build();
			
			Mono<UserDTO> user = webCliente.get()
					.uri("/user/cpf/"+cpf+"?key="+key)
					.retrieve()
					.bodyToMono(UserDTO.class);
			
			return user.block();
		}catch (Exception e) {
			throw new UserNotFoundException();
		}
	}
	
}
