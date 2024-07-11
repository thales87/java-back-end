package com.livroback.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.client.dto.UserDTO;
import com.client.exception.UserNotFoundException;
import com.livroback.dto.DTOConverter;
import com.livroback.model.User;
import com.livroback.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository ;
	
	public UserDTO findById(Long userId) {
		User usuario = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("usuario não encontrado"));
		return DTOConverter.convert(usuario);
	}
	
	public UserDTO save(UserDTO userDTO) {
		userDTO.setKey(UUID.randomUUID().toString());
		userDTO.setDataCadastro(LocalDateTime.now());
		User user = userRepository.save(User.convert(userDTO));
		return DTOConverter.convert(user);
	}
	
	public UserDTO delete(Long userID) {
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new RuntimeException());
		userRepository.delete(user);
		return DTOConverter.convert(user);
	}
	
	public UserDTO findByCpfAndKey(String cpf, String key) {
		User usuario = userRepository.findByCpfAndKey(cpf,key);
		if(usuario != null) {
			return DTOConverter.convert(usuario);
		}
		throw new UserNotFoundException();
	}
	
	public List<UserDTO> queryByName(String name){
		List<User> usuarios = userRepository.queryByNomeLike(name);
		return usuarios
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}
	
	public UserDTO editUser(Long userId, UserDTO userDTO) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
		if(userDTO.getEmail()!=null && 
				!user.getEmail().equals(userDTO.getEmail())) {
			user.setEmail(userDTO.getEmail());
		}
		if(userDTO.getTelefone()!=null && 
				!user.getTelefone().equals(userDTO.getTelefone())) {
			user.setTelefone(userDTO.getTelefone());
		}
		if(userDTO.getEndereco()!=null && 
				!user.getEndereco().equals(userDTO.getEndereco())) {
			user.setEndereco(userDTO.getEndereco());
		}
		user=userRepository.save(user);
		return DTOConverter.convert(user);
	}
	
	public Page<UserDTO> getAllPage(Pageable page){
		Page<User> users = userRepository.findAll(page);
		return users.map(DTOConverter::convert);
	}
	
	public List<UserDTO> getAll(){
		List<User> usuarios = userRepository.findAll();
		return usuarios
				.stream()
				.map(DTOConverter::convert)
				.collect(Collectors.toList());
	}
	
}
