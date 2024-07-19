package com.peroductservice.productservice.services;

import com.peroductservice.productservice.exceptions.ProductNotFoundException;
import com.peroductservice.productservice.models.Category;
import com.peroductservice.productservice.models.Product;
import com.peroductservice.productservice.repositories.CategoryRepository;
import com.peroductservice.productservice.repositories.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

//import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Repository("mySQLProductServiceRepository")
public class MySQLProductService implements ProductService {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public MySQLProductService(CategoryRepository categoryRepository, ProductRepository productRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findProductById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String description, String imageUrl, String category, double price) {
        Product saveProduct = new Product();
        saveProduct.setTitle(title);
        saveProduct.setDescription(description);
        saveProduct.setPrice(price);
//        Category saveCategory = new Category();
        Category saveCategory = categoryRepository.getCategoryByTitle(category);
        if(saveCategory == null){
            saveCategory = new Category();
            saveCategory.setTitle(category);
        }
        saveProduct.setCategory(saveCategory);
        saveProduct.setImageUrl(imageUrl);
        return productRepository.save(saveProduct);
    }

    @Override
    public Product updateProduct(Long productId, String title, String description, String imageUrl, String category, double price) {
        Product saveProduct = new Product();
        saveProduct.setId(productId);
        saveProduct.setTitle(title);
        saveProduct.setDescription(description);
        saveProduct.setPrice(price);
        saveProduct.setImageUrl(imageUrl);
        Category saveCategory = categoryRepository.getCategoryByTitle(category);
        if(saveCategory == null){
            saveCategory = new Category();
            saveCategory.setTitle(category);
        }
        saveProduct.setCategory(saveCategory);
        return productRepository.save(saveProduct);
    }

    @Override
    public Product deleteProduct(Long productId) {
        return null;
    }

    @Override
    public List<Product> getAllProductsPaged(int pageNo, int pageSize) {
        Pageable pageable = (Pageable) PageRequest.of(pageNo,pageSize);
        List<Product> productList = new ArrayList<>();
        for(Product product : productRepository.findAll(pageable)){
            productList.add(product);
        }
        return productList;
    }
    @Override
    public List<Product> findAllByPrice(double price,int pageNo, int pageSize){
        return productRepository.findAllByPrice(price, PageRequest.of(pageNo,pageSize));
    }
}
