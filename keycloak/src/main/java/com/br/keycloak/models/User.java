package com.br.keycloak.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {
	
	@NotBlank(message = "username is required")
	private String username;
	@Size(min = 8,max=20,message = "password must be between 8 and 20 characters")
	private String password;
}
