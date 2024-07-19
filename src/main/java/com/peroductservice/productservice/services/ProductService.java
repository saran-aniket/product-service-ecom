package com.peroductservice.productservice.services;

import com.peroductservice.productservice.exceptions.ProductNotFoundException;
import com.peroductservice.productservice.models.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(Long productId) throws ProductNotFoundException;
    public List<Product> getAllProducts();
    public Product createProduct(String title,
                                 String description,
                                 String imageUrl,
                                 String category,
                                 double price);
    public Product updateProduct(Long productId,
                                 String title,
                                 String description,
                                 String imageUrl,
                                 String category,
                                 double price);

    public Product deleteProduct(Long productId);
    public List<Product> getAllProductsPaged(int pageNo, int pageSize);
    public List<Product> findAllByPrice(double price, int pageNo, int pageSize);
}
