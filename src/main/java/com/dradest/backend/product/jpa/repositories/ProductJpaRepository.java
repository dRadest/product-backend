package com.dradest.backend.product.jpa.repositories;

import com.dradest.backend.product.jpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
}
