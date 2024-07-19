package com.peroductservice.productservice.dtos;

import com.peroductservice.productservice.models.Category;
import com.peroductservice.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;

    public Product convertDTOToProduct(){
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setImageUrl(image);
        product.setPrice(price);
        Category category = new Category();
        category.setTitle(this.category);
        product.setCategory(category);
        return product;
    }
}
