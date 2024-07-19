package com.peroductservice.productservice.services;

import com.peroductservice.productservice.dtos.FakeStoreDTO;
import com.peroductservice.productservice.exceptions.ProductNotFoundException;
import com.peroductservice.productservice.models.Product;
import com.peroductservice.productservice.repositories.ProductRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final ProductRepository productRepository;
    RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
    }
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        FakeStoreDTO fakeStoreDTO = this.restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+productId,
                FakeStoreDTO.class
        );
        if(fakeStoreDTO == null){
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found. " +
                            "Please try a product with id less than 21");
        }
        return fakeStoreDTO.convertDTOToProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreDTO[] fakeStoreDTOList = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreDTO[].class
        );
        List<Product> productList = new ArrayList<>();

        for(FakeStoreDTO fakeStoreDTO: fakeStoreDTOList){
            productList.add(fakeStoreDTO.convertDTOToProduct());
        }

        return productList;
    }



    @Override
    public Product createProduct(String title, String description, String imageUrl, String category, double price) {
        FakeStoreDTO requestDTO = new FakeStoreDTO();
        requestDTO.setTitle(title);
        requestDTO.setDescription(description);
        requestDTO.setPrice(price);
        requestDTO.setCategory(category);
        requestDTO.setImage(imageUrl);
        FakeStoreDTO responseDTO = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                requestDTO,
                FakeStoreDTO.class
        );

        return responseDTO.convertDTOToProduct();
    }

    @Override
    public Product updateProduct(Long productId, String title, String description, String imageUrl, String category, double price) {
        FakeStoreDTO requestDTO = new FakeStoreDTO();
        requestDTO.setId(productId);
        requestDTO.setTitle(title);
        requestDTO.setDescription(description);
        requestDTO.setPrice(price);
        requestDTO.setCategory(category);
        requestDTO.setImage(imageUrl);
        HttpEntity<FakeStoreDTO> requestEntity = new HttpEntity<>(requestDTO);
        ResponseEntity<FakeStoreDTO> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/"+ productId,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreDTO.class
        );

        return responseEntity.getBody().convertDTOToProduct();
    }

    @Override
    public Product deleteProduct(Long productId) {
        ResponseEntity<FakeStoreDTO> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/"+ productId,
                HttpMethod.DELETE,
                null,
                FakeStoreDTO.class
        );

        FakeStoreDTO responseDTO = responseEntity.getBody();
        return responseDTO.convertDTOToProduct();
    }

    @Override
    public List<Product> getAllProductsPaged(int pageNo, int pageSize) {
        return List.of();
    }

    @Override
    public List<Product> findAllByPrice(double price, int pageNo, int pageSize) {
        return List.of();
    }
}
