package com.peroductservice.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
}
