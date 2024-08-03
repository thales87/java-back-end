package com.br.keycloak.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSController {

	@GetMapping("/admin")
	@PreAuthorize("hasAnyAuthority('ADMIN_READ','ADMIN_WRITE')")
	public String adminAccess() {
		return "pode ir admin";
	}
	
	
	@GetMapping("/operacional")
	@PreAuthorize("hasAnyAuthority('OPERACIONAL_READ','OPERACIONAL_WRITE')")
	public String operacionalAccess() {
		return "pode ir operacional";
	}
	
}
