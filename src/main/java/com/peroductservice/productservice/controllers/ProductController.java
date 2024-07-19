package com.peroductservice.productservice.controllers;

import com.peroductservice.productservice.dtos.ProductRequestDTO;
import com.peroductservice.productservice.dtos.ProductResponseDTO;
import com.peroductservice.productservice.exceptions.ProductNotFoundException;
import com.peroductservice.productservice.models.Product;
import com.peroductservice.productservice.services.FakeStoreProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.peroductservice.productservice.services.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final FakeStoreProductService fakeStoreProductService;

    ProductController(@Qualifier("mySQLProductServiceRepository") ProductService productService, ModelMapper modelMapper, FakeStoreProductService fakeStoreProductService){
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.fakeStoreProductService = fakeStoreProductService;
    }

    @GetMapping("/id/{id}")
    public ProductResponseDTO getProductDetails(@PathVariable("id") Long productId) throws ProductNotFoundException {
          Product product =  productService.getSingleProduct(productId);
        if(product == null){
            throw new ProductNotFoundException(
                    "No products found"
            );
        }
          return convertToProductResponseDTO(product);
    }

//    @GetMapping("/products")
//    public ResponseEntity<List<ProductResponseDTO>> getAllProductDetails() throws ProductNotFoundException {
//        List<Product> productList =  productService.getAllProducts();
//        if(productList.isEmpty()){
//            throw new ProductNotFoundException(
//                    "No products found"
//            );
//        }
//        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
//        for(Product product: productList){
//            ProductResponseDTO responseDTO = convertToProductResponseDTO(product);
//            productResponseDTOList.add(responseDTO);
//        }
//        return new ResponseEntity<>(productResponseDTOList, HttpStatus.OK);
//    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<ProductResponseDTO>> getAllProductDetailsPaged(@PathVariable("price") double price, @RequestParam(value="pageNo",required = false,defaultValue = "0") int pageNo, @RequestParam(value="pageSize",required = false,defaultValue = "1") int pageSize) throws ProductNotFoundException {
//        List<Product> productList =  productService.getAllProductsPaged(pageNo,pageSize);
        List<Product> productList =  productService.findAllByPrice(price,pageNo,pageSize);
        if(productList.isEmpty()){
            throw new ProductNotFoundException(
                    "No products found"
            );
        }
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        for(Product product: productList){
            ProductResponseDTO responseDTO = convertToProductResponseDTO(product);
            productResponseDTOList.add(responseDTO);
        }
        return new ResponseEntity<>(productResponseDTOList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createNewProduct(@RequestBody ProductRequestDTO productRequestDto){
        Product product = productService.createProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImageUrl(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice() );
        ProductResponseDTO productResponseDTO = convertToProductResponseDTO(product);
        return new ResponseEntity<>(productResponseDTO,HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> putProduct(@PathVariable("id") Long productId, @RequestBody ProductRequestDTO productRequestDto) throws ProductNotFoundException {
        Product product = fakeStoreProductService.updateProduct(
                productId,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImageUrl(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice());
        return new ResponseEntity<>(convertToProductResponseDTO(product),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        Product product = fakeStoreProductService.deleteProduct(productId);
        return new ResponseEntity<>(convertToProductResponseDTO(product),HttpStatus.OK);
    }






    public ProductResponseDTO convertToProductResponseDTO(Product product){
        String category = product.getCategory().getTitle();
        ProductResponseDTO productResponseDTO = modelMapper.map(product, ProductResponseDTO.class);
        productResponseDTO.setCategory(category);
        return productResponseDTO;
    }
}
