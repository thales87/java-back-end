package com.livroback.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.client.dto.UserDTO;
import com.livroback.dto.DTOConverter;
import com.livroback.model.User;
import com.livroback.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	public void testListAllUsers() {
		List<User> users = new ArrayList<>();
		users.add(getUser(1L, "UserName", "123"));
		users.add(getUser(2L, "UserName 2", "321"));
		
		Mockito.when(userRepository.findAll()).thenReturn(users);
		
		List<UserDTO> userReturn = userService.getAll();
		Assertions.assertEquals(2, userReturn.size());
	}
	
	@Test
	public void testSaveUser() {
		User userDB = getUser(1L,"UserName","123");
		UserDTO userDTO = DTOConverter.convert(userDB);
		
		Mockito.when(userRepository.save(Mockito.any()))
			.thenReturn(userDB);
		
		UserDTO user = userService.save(userDTO);
		Assertions.assertEquals("UserName", user.getNome());
		Assertions.assertEquals("123", user.getCpf());
	}
	
	@Test
	public void testEditUser() {
		User userDB = getUser(1L,"UserName","123");
		
		Mockito.when(userRepository.findById(1L))
			.thenReturn(Optional.of(userDB));
		Mockito.when(userRepository.save(Mockito.any()))
		.thenReturn(userDB);
		
		UserDTO userDTO = DTOConverter.convert(userDB);
		userDTO.setEndereco("novo End");
		userDTO.setTelefone("1234");
		
		UserDTO user = userService.editUser(1L, userDTO);
		
		Assertions.assertEquals("novo End", user.getEndereco());
		Assertions.assertEquals("1234", user.getTelefone());
	}
	
	public static User getUser(Long id, String nome,String cpf) {
		User user = new User();
		user.setId(id);
		user.setNome(nome);
		user.setCpf(cpf);
		user.setEndereco("endereco");
		user.setTelefone("5432");
		return user;
	}
	
}
