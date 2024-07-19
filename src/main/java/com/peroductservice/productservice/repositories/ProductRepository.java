package com.peroductservice.productservice.repositories;

import com.peroductservice.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@NonNullApi
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(Long Id);
    Product save(Product product);
    Product findProductById(Long productId);
    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    List<Product> findAllByPrice(double price,Pageable pageable);
    List<Product> findProductByCategory_Title(String title);
}
