package com.br.keycloak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.keycloak.models.User;
import com.br.keycloak.services.LoginService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody User user){
		return loginService.login(user);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(@RequestParam("refresh_token") String refreshToken){
		return loginService.refreshToken(refreshToken);
	}
}
