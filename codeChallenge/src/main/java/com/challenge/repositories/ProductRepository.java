package com.challenge.repositories;

import com.challenge.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUser_IdEquals(Long id);

}