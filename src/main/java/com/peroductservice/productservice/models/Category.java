package com.peroductservice.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;


@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String title;
    private String description;
    @OneToMany(mappedBy = "category")//map the Products category field here to prevent unexpected ORM behaviour
    @Fetch(FetchMode.JOIN) //for N+1 problem JOIN-Eager, SELECT - N+1, SUBSELECT - N+1 Solution(2 DB queries)
    List<Product> products;
}