package com.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.produtos.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
