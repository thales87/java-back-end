package com.livroback.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.client.dto.UserDTO;
import com.livroback.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

	private final UserService userService;

	@GetMapping
	public List<UserDTO> getUsuarios() {
		return userService.getAll();
	}

	@GetMapping("/search")
	public List<UserDTO> getUsuariosNome(@RequestParam(name = "nome", required = true) String nome) {
		return userService.queryByName(nome);
	}

	@GetMapping("/{id}")
	public UserDTO getUsuarioID(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	@GetMapping("/cpf/{cpf}")
	public UserDTO getUsuarioCPF(@PathVariable String cpf) {
		return userService.findByCpf(cpf);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT)
	public void remover(@PathVariable Long id) {
		userService.delete(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDTO inserir(@RequestBody @Valid UserDTO userDTO) {
		return userService.save(userDTO);
	}

	@PatchMapping("/{id}")
	public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		return userService.editUser(id, userDTO);
	}

	@GetMapping("/pageable")
	public Page<UserDTO> getUsersPage(Pageable pageable) {
		return userService.getAllPage(pageable);
	}
}
