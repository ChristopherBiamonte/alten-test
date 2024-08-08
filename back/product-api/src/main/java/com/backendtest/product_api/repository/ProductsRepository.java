package com.backendtest.product_api.repository;

import com.backendtest.product_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
