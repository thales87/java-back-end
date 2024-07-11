package com.livroback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livroback.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByCpfAndKey(String cpf, String key);
	List<User> queryByNomeLike(String name);
}
